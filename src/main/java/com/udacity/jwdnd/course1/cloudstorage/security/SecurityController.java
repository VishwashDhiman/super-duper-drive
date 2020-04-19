package com.udacity.jwdnd.course1.cloudstorage.security;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.entities.Files;
import com.udacity.jwdnd.course1.cloudstorage.entities.Notes;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repository.CredentialsRepository;
import com.udacity.jwdnd.course1.cloudstorage.repository.FilesRepository;
import com.udacity.jwdnd.course1.cloudstorage.repository.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
public class SecurityController {
    // Repositories
    UserRepository userRepository;
    NoteRepository notesRepository;
    CredentialsRepository credentialsRepository;
    FilesRepository filesRepository;
    PasswordEncoder passwordEncoder;
    UserDetails userDetails;

    @GetMapping({"login", "signin"})
    public String login(Model model) {
        String signupSuccess = (String) model.asMap().get("signup");
        model.addAttribute("signup", null);
        return "login";
    }

    @GetMapping("/")
    public String defaultView() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userDetails.setName(auth.getName());
        System.out.println("LOGGEDDD INNNADADASD===>>>" + auth.getName());
        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logoutPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            System.out.println("NULLADLASD");
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        System.out.println("ADADASD");
        model.addAttribute("logout", true);
        return "login";
    }

    @GetMapping("home")
    public String home(Model model) {
        String userId = userDetails.getName();

        String success = (String) model.asMap().get("success");
        String error = (String) model.asMap().get("error");
            
        model.addAttribute("success", null);
        model.addAttribute("error", null);

        System.out.println("Success===>>>" + success);
        System.out.println("Error===>>>" + error);


        if (success != null && success != "") {
            model.addAttribute("success", success);
        }

        if (error != null && error != "") {
            model.addAttribute("error", error);
        }

        List<Notes> note = notesRepository.getAllNotesById(userId);
        List<Credentials> credential = credentialsRepository.getAllCredentialsById(userId);
        List<Files> file = filesRepository.getAllFiles(userId);

        for(Credentials cred : credential) {
            cred.encodedPassword = passwordEncoder.encode(cred.password);
        }

        model.addAttribute("notes", note);
        model.addAttribute("credentials", credential);
        model.addAttribute("files", file);
        return "home";
    }

    @GetMapping("signup")
    public String signUpPage(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("signup")
    public String signUp(Model model, @ModelAttribute User user) {
        User dbUser = userRepository.findByUsername(user.getUsername());
        model.addAttribute("success", null);
        model.addAttribute("error", null);
        if(dbUser == null){
            user.password = passwordEncoder.encode(user.password);
            userRepository.insert(user);
            model.addAttribute("success", "Signup Success!!");
        } else {
            model.addAttribute("error", "Signup Error.");
        }
        return "signup";
    }

    @PostMapping("notes")
    public String notes(@ModelAttribute Notes note, RedirectAttributes redirectAttributes) {
        System.out.println("NOTES===>>" + note.noteId + "   ssad==>>" + note);
        String userId = userDetails.getName();
        System.out.println("USERID ===>>" + userId);
        if (note.noteId == null || note.noteId == "") {
            notesRepository.insertNotes(userId, note);
            redirectAttributes.addFlashAttribute("success", "Note added successfully!!!");
        } else {
            notesRepository.updateNote(userId, note);
            redirectAttributes.addFlashAttribute("success", "Note updated successfully!!!");
        }
        return "redirect:/home";
    }

    @GetMapping("deleteNote")
    public String deleteNote(@RequestParam String id, RedirectAttributes redirectAttributes) {
        System.out.println("NOTES===>>" + id);
        String userId = userDetails.getName();
        Notes note = notesRepository.getNote(userId, id);
        if(note != null) {
            notesRepository.deleteNoteById(userId, id);
            redirectAttributes.addFlashAttribute("success", "Note deleted successfully!!!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Note not found");
        }

        return "redirect:/home";
    }

    @PostMapping("credentials")
    public String credentials(@ModelAttribute Credentials credential, RedirectAttributes redirectAttributes) {
        System.out.println("credential===>>" + credential.credentialId + "   ssad==>>" + credential);
        String userId = userDetails.getName();
        System.out.println("USERID ===>>" + userId);
        if (credential.credentialId == null || credential.credentialId == "") {
            credentialsRepository.insertCredentials(userId, credential);
            redirectAttributes.addFlashAttribute("success", "Credential added successfully!!!");
        } else {
            credentialsRepository.updateCredentials(userId, credential);
            redirectAttributes.addFlashAttribute("success", "Credential updated successfully!!!");
        }
        return "redirect:/home";
    }

    @GetMapping("deleteCredential")
    public String deleteCredentials(@RequestParam String id, RedirectAttributes redirectAttributes) {
        System.out.println("credential===>>" + id);
        String userId = userDetails.getName();
        Credentials credential = credentialsRepository.getCredential(userId, id);
        if(credential != null) {
            credentialsRepository.deleteCredentialById(userId, id);
            redirectAttributes.addFlashAttribute("success", "Credential deleted successfully!!!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Credential not found");
        }
        return "redirect:/home";
    }

    @PostMapping("files")
    public String files(@ModelAttribute MultipartFile fileUpload, RedirectAttributes redirectAttributes) throws IOException {

        System.out.println("FILE===>>>" +  fileUpload.getOriginalFilename());
        String userId = userDetails.getName();
        Files existingFile = filesRepository.getFileById(userId, fileUpload.getOriginalFilename());
        System.out.println("FILE SEARCH===>>>" +  existingFile);
        if(existingFile == null) {
            Files file = new Files();
            file.fileName = fileUpload.getOriginalFilename();
            file.contentType = fileUpload.getContentType();
            file.fileSize = String.valueOf(fileUpload.getSize());
            file.fileData = fileUpload.getBytes();
            filesRepository.insertFiles(userId, file);
            redirectAttributes.addFlashAttribute("success", "File added successfully!!!");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "File already exists with same name");
        }
        return "redirect:/home";
    }

    @GetMapping("deleteFile")
    public String deleteFile(@RequestParam String id, RedirectAttributes redirectAttributes) {
        System.out.println("FILEID===>>>" +  id);
        String userId = userDetails.getName();
        Files file = filesRepository.getFileById(userId, id);
        if (file != null) {
            filesRepository.deleteFileById(userId, id);
            redirectAttributes.addFlashAttribute("success", "File deleted successfully!!!");
        } else {
            redirectAttributes.addFlashAttribute("error", "File not found");
        }
        return "redirect:/home";
    }

    @GetMapping("downloadFile")
    @ResponseBody
    public ResponseEntity downloadFile(@RequestParam String name, RedirectAttributes redirectAttributes) {

        String userId = userDetails.getName();
        Files file = filesRepository.getFileById(userId, name);
        System.out.println("FILE====>>" + file);
        ByteArrayResource resource = new ByteArrayResource(file.fileData);
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.fileName)
                // Content-Type
                .contentType(MediaType.valueOf(file.contentType)) //
                // Content-Lengh
                .contentLength(Long.parseLong(file.fileSize)) //
                .body(resource);
    }
}


