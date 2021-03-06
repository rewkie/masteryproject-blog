package com.wcci.masteryblog.blog.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wcci.masteryblog.blog.models.Genre;
import com.wcci.masteryblog.blog.repositories.AuthorsRepository;
import com.wcci.masteryblog.blog.repositories.GenreRepository;
import com.wcci.masteryblog.blog.repositories.OctoRepository;
import com.wcci.masteryblog.blog.repositories.PostsRepository;

@Controller
@RequestMapping("/genres")
public class GenreController {
	
	@Resource
	PostsRepository postsRepo;
	@Resource
	AuthorsRepository authorsRepo;
	@Resource
	GenreRepository genreRepo;
	@Resource 
	OctoRepository octoRepo;
	
	
	@GetMapping("")
	public String viewAllGenres(Model model) {
	model.addAttribute("genres", genreRepo.findAll());
	model.addAttribute("octothorps", octoRepo.findAll());
	return"genres";
	}
	
	@PostMapping("/addgenre")
	public String addGenre(Model model, String genreName) {
	model.addAttribute(genreRepo.findAll());
	Genre genreToAdd = new Genre(genreName);
	genreRepo.save(genreToAdd);
	return"redirect:/genres";
	}
	
	@GetMapping("/{id}")
	public String viewGenre(@PathVariable Long id, Model model) {
	model.addAttribute("genre", genreRepo.findById(id).get());
	model.addAttribute("posts", postsRepo.findAll());
	model.addAttribute("octothorps", octoRepo.findAll());
	return"singlegenre";
	}
}
