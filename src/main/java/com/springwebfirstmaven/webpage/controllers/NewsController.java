package com.springwebfirstmaven.webpage.controllers;

import com.springwebfirstmaven.webpage.models.Post;
import com.springwebfirstmaven.webpage.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class NewsController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/news")
    public String newsMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("post", posts);
        return "newsmain";
    }

    @GetMapping("/news/add")
    public String newsAdd(Model model) {
        return "newsadd";
    }

    @PostMapping("/news/add")
    public String newsPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/news";
    }

    @GetMapping("/news/{id}")
    public String newsDetails(@PathVariable(value = "id") long id, Model model) {
        if ((!postRepository.existsById(id))) {
            return "redirect:/news";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "newsdetails";
    }

    @GetMapping("/news/{id}/edit")
    public String newsEdit(@PathVariable(value = "id") long id, Model model) {
        if ((!postRepository.existsById(id))) {
            return "redirect:/news";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "newsedit";
    }

       @PostMapping("/news/{id}/edit")
    public String newsPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/news";
    }

    @PostMapping("/news/{id}/remove")
    public String newsPostRemove(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/news";
    }
}

