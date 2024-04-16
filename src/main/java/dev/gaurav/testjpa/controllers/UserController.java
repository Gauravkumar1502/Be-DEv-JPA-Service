package dev.gaurav.testjpa.controllers;

import dev.gaurav.testjpa.DTO.APIResponseDTO;
import dev.gaurav.testjpa.DTO.LoginUserDTO;
import dev.gaurav.testjpa.DTO.FriendRequestDTO;
import dev.gaurav.testjpa.DTO.RegisterUserDTO;
import dev.gaurav.testjpa.exceptions.AlreadyExistsException;
import dev.gaurav.testjpa.exceptions.DoesNotExistException;
import dev.gaurav.testjpa.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponseDTO> registerUser(@RequestBody RegisterUserDTO registerUserRequest) {
        try {
            userService.saveUser(registerUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponseDTO("User registered successfully",true));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponseDTO(e.getMessage(),false));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponseDTO("Internal server error",false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponseDTO("Request body is not correct",false));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponseDTO> loginUser(@RequestBody LoginUserDTO loginUserRequest) {
        try {
            if (!userService.verifyPassword(loginUserRequest)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponseDTO("Invalid username or password",false));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new APIResponseDTO("Login successful",true));
        } catch (DoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponseDTO(e.getMessage(),false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponseDTO("Request body is not correct",false));
        }
    }

    @PostMapping("/addFriend")
    public ResponseEntity<String> addFriend(@RequestParam(value = "username") String username, @RequestParam(value = "friendUsername") String friendUsername) {
        try {
            if (userService.sendFriendRequest(username, friendUsername)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Friend request sent to " + friendUsername);
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (DoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is not correct");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is not correct");
    }
    @PostMapping("/acceptFriend")
    public ResponseEntity<String> acceptFriend(@RequestParam(value = "username") String username, @RequestParam(value = "friendUsername") String friendUsername) {
        try {
            if (userService.acceptFriendRequest(username, friendUsername)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Friend request accepted from " + friendUsername);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is not correct");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is not correct");
    }

    @GetMapping("/pendingFriendRequests")
    public ResponseEntity<List<FriendRequestDTO>> pendingFriendRequests(@RequestParam(value = "username") String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getPendingFriendRequests(username));
        } catch (DoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/friends")
    public ResponseEntity<Set<FriendRequestDTO>> friends(@RequestParam(value = "username") String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getFriends(username));
        } catch (DoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}