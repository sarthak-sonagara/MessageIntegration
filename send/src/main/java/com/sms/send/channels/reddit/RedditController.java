package com.sms.send.channels.reddit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

@Controller
@RequestMapping("/reddit")
public class RedditController{
    private final RedditGrabber redditGrabber;
    private final RedditService redditService;
    @Autowired
    protected RedditController(RedditGrabber redditGrabber, RedditService redditService) {
        this.redditGrabber = redditGrabber;
        this.redditService = redditService;
    }

    @GetMapping("/callback")
    public ResponseEntity<String> redditCallback(HttpServletResponse response, @RequestParam(required = false) String state, @RequestParam(required = false) String code, @RequestParam(required = false) String error){
        if(error!=null){
            Cookie cookie = new Cookie("access_token",null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
        if(!redditService.verifyState(state)){
            return new ResponseEntity<>("State changed error.", HttpStatus.EXPECTATION_FAILED);
        }
        try {
            String accessToken = redditService.getAccessToken(code);
            String cookieString = "access_token="+accessToken+"; Max-Age=3600";
            return getRedirectResponseEntity(ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/reddit/data")
                    .build()
                    .toUriString(),cookieString);
        } catch (IOException e) {
            return new ResponseEntity<>("IO error",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            return new ResponseEntity<>("Interrupted error",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (StatusCodeException e) {
            System.out.println(e.getMessage());
            return getRedirectResponseEntity(redditService.getUrl(),null);
        }
    }

    @GetMapping("/data")
    public ResponseEntity<String> getMyDetailsReddit(@NotNull HttpServletRequest request, HttpServletResponse httpResponse){
        Cookie[] cookies = request.getCookies();
        String accessToken = getCookieValue("access_token", cookies);
        if(accessToken==null){
            return getRedirectResponseEntity(redditService.getUrl(),null);
        }
        try {
            String after = getCookieValue("after", cookies);
            String redditResponse = redditService.getMyDetails(accessToken,after);
            Cookie cookie = new Cookie("after",getAfterValue(redditResponse));
            cookie.setMaxAge(60*60);
            httpResponse.addCookie(cookie);
            redditGrabber.convertAndPutMessage(redditResponse);
            return new ResponseEntity<>("This is saved in database \n" + redditResponse,HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("IO error",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            return new ResponseEntity<>("Interrupted error",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (StatusCodeException e) {
            System.out.println(e.getMessage());
            return getRedirectResponseEntity(redditService.getUrl(),null);
        }
    }

    @GetMapping("/url")
    public ResponseEntity<String> getRedditUserUrl(){
        String url = redditService.getUrl();
        return ResponseEntity.ok(url);
    }
    private ResponseEntity<String> getRedirectResponseEntity(String newUri, String cookieString){
        // Create HTTP headers with the redirect location
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, newUri);
        headers.add(HttpHeaders.SET_COOKIE,cookieString);
        // Return ResponseEntity with HTTP status 302 Found and redirect headers
        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }

    private String getCookieValue(String cookieName, Cookie[] cookies){
        if(cookies==null)
            return null;
        for(Cookie cookie : cookies){
            if(cookieName.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    private String getAfterValue(String response){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(response);
            return node.get("data").get("after").asText();
        } catch (JsonProcessingException e) {
            System.out.println("cannot find after field.");
            return null;
        }
    }

}
