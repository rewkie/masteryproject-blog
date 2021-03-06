package com.wcci.masteryblog.blog.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wcci.masteryblog.blog.models.Author;
import com.wcci.masteryblog.blog.models.Genre;
import com.wcci.masteryblog.blog.models.Octothorp;
import com.wcci.masteryblog.blog.models.Post;
import com.wcci.masteryblog.blog.repositories.AuthorsRepository;
import com.wcci.masteryblog.blog.repositories.GenreRepository;
import com.wcci.masteryblog.blog.repositories.OctoRepository;
import com.wcci.masteryblog.blog.repositories.PostsRepository;

@Controller
@RequestMapping("/posts")
public class PostController {

	@Resource
	PostsRepository postsRepo;
	@Resource
	AuthorsRepository authorsRepo;
	@Resource
	GenreRepository genreRepo;
	@Resource
	OctoRepository octoRepo;

	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("posts", postsRepo.findAll());
		model.addAttribute("octothorps", octoRepo.findAll());
		return "posts";

	}

	@GetMapping("/addpost")
	public String addPost(Model model) {
		model.addAttribute(postsRepo.findAll());
		model.addAttribute("authors", authorsRepo.findAll());
		model.addAttribute("genres", genreRepo.findAll());
		model.addAttribute("octothorps", octoRepo.findAll());
		return "addpost";
	}

	@PostMapping("/addpost")
	public String addPost(String lastName, String genreName, String tagName, String postTitle, String postContent) {
		String[] tags = tagName.split(",");
		Genre genre = genreRepo.findByGenreName(genreName);
		Author author = authorsRepo.findByLastName(lastName);
		Octothorp tag = octoRepo.findByTagName(tags[0]);
		Post newPost = postsRepo.save(new Post(postTitle, author, genre, postContent, tag));
		for (int i = 1; i < tags.length; i++) {
			newPost.addOctoToPostOctos(octoRepo.findByTagName(tags[i]));
		}
		postsRepo.save(newPost);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String viewPost(@PathVariable Long id, Model model) {
		model.addAttribute("post", postsRepo.findById(id).get());
		model.addAttribute("authors", authorsRepo.findAll());
		model.addAttribute("octothorps", octoRepo.findAll());
		return "singlepost";
	}

	@PostMapping("/{id}")
	public String addAdditionalAuthor(@PathVariable Long id, String lastName) {
		Post postToAddTo = postsRepo.findById(id).get();
		Author authorToFind = authorsRepo.findByLastName(lastName);
		if (!postToAddTo.getAuthors().contains(authorToFind)) {
			postToAddTo.addAuthorToPostAuthors(authorToFind);
			postsRepo.save(postToAddTo);
		}
		return "redirect:/posts/" + id;
	}

//	@PostMapping("/{id}/addocto")
//	public String addAdditionalOctothorp(@PathVariable Long id, String octoName) {
//		Post postToAddTo = postsRepo.findById(id).get();
//		Octothorp octoToFind = octoRepo.findByTagName(octoName);
//		if(!postToAddTo.getOctos().contains(octoToFind)) {
//			postToAddTo.addOctoToPostOctos(octoToFind);
//			postsRepo.save(postToAddTo);
//			}
//		return"redirect:/posts/" +id;
//	}

	@PostMapping("/{id}/addocto")
	public String addAdditionalOctothorp(@PathVariable Long id, String octoName) {
		Post postToAddTo = postsRepo.findById(id).get();
		Octothorp octoToFind = octoRepo.findByTagName(octoName);
		for (Octothorp octothorp : octoRepo.findAll()) {
			if (!postToAddTo.getOctos().contains(octoToFind)) {
				postToAddTo.addOctoToPostOctos(octoToFind);
				postsRepo.save(postToAddTo);
			}
		}
		return "redirect:/posts/" + id;
	}

//		Octothorp octoToFind = octoRepo.findByTagName(octoName);
//		if(!postToAddTo.getOctos().contains(octoToFind)) {
//			postToAddTo.addOctoToPostOctos(octoToFind);
//			postsRepo.save(postToAddTo);
//			}
//		return"redirect:/posts/" +id;
//	}
}