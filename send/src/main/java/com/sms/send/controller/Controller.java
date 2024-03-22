package com.sms.send.controller;
import com.sms.send.reddit.RedditAuthenticationService;
import com.sms.send.channels.ChannelService;
import com.sms.send.entities.TeamsMessage;
import com.sms.send.reddit.StatusCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@org.springframework.stereotype.Controller
public class Controller {
    private final ChannelService channelService;
    private final RedditAuthenticationService redditAuthenticationService;
    @Autowired
    public Controller(ChannelService channelService, RedditAuthenticationService redditAuthenticationService) {
        this.channelService = channelService;
        this.redditAuthenticationService = redditAuthenticationService;
    }
    @GetMapping("/teams")
    public ResponseEntity<String> putMessageFromTeamsRest(){
        channelService.putMessage(new TeamsMessage("aaje"));
        return new ResponseEntity<>("tada", HttpStatus.ACCEPTED);
    }

    @GetMapping("/reddit/callback")
    public ResponseEntity<String> redditCallback(HttpServletResponse response, @RequestParam(required = false) String state, @RequestParam(required = false) String code, @RequestParam(required = false) String error){
        if(error!=null){
            Cookie cookie = new Cookie("access_token",null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
        if(!redditAuthenticationService.verifyState(state)){
            return new ResponseEntity<>("State changed error.", HttpStatus.EXPECTATION_FAILED);
        }
        try {
            String accessToken = redditAuthenticationService.getAccessToken(code);
            Cookie cookie = new Cookie("access_token", accessToken);
            cookie.setMaxAge(60*60);
            response.addCookie(cookie);
        } catch (IOException e) {
            return new ResponseEntity<>("IO error",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            return new ResponseEntity<>("Interrupted error",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (StatusCodeException e) {
            return new ResponseEntity<>("Request failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("http://localhost:8081/Gradle___com_sms_send___send_1_0_SNAPSHOT_war__exploded_/reddit/me", HttpStatus.OK);
    }

    @GetMapping("/reddit/me")
    public ResponseEntity<String> getMyDetailsReddit(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        for(Cookie cookie : cookies){
            if("access_token".equals(cookie.getName())){
                accessToken = cookie.getValue();
            }
        }
        if(accessToken==null){
            return new ResponseEntity<>("Not authenticated, go to /reddit/url first", HttpStatus.OK);
        }
        try {
            String response = redditAuthenticationService.getMyDetails(accessToken);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("IO error",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            return new ResponseEntity<>("Interrupted error",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (StatusCodeException e) {
            return new ResponseEntity<>("Not authenticated, go to /reddit/url first", HttpStatus.OK);
        }
    }

    @GetMapping("/reddit/url")
    public ResponseEntity<String> getRedditUserUrl(){
        String url = redditAuthenticationService.getUrl();
        return ResponseEntity.ok(url);
    }

}
