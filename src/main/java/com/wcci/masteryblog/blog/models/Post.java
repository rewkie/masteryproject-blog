package com.wcci.masteryblog.blog.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;



@Entity
public class Post {

	@Id
	@GeneratedValue
	private Long id;
	private String postTitle;
	private LocalDateTime date;
	@Lob
	private String postContent;


	@ManyToMany
	private Collection<Author> authors;
	@ManyToMany
	private Collection<Octothorp> octos;
	@ManyToOne
	private Genre genre;
	
	public Post() {}

	public Post(String postTitle, Author author, Genre genre, String postContent, Octothorp ...octo) {
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.genre = genre;
		this.date = LocalDateTime.now();
		this.authors = new ArrayList<>(Arrays.asList(author));
		this.octos = new ArrayList<>(Arrays.asList(octo));
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public Long getId() {
		return id;
	}

	public Collection<Author> getAuthors() {
		return authors;
	}
	
	public Collection<Octothorp> getOctos() {
		return octos;
	}

	public Genre getGenre() {
		return genre;
	}
	
	public void addAuthorToPostAuthors(Author authorToFind) {
		authors.add(authorToFind);
		
	}
	
	public void addOctoToPostOctos(Octothorp OctoToAdd) {
		octos.add(OctoToAdd);
	}

	/* because there is a ManyToMany relationship where a Post can have multiple authors,
	 * this method will print all authors first and last names.
	 */
	public String getAuthorNames() {
		List<String> nameList = new ArrayList<>();
		for (Author author : authors) {
			String authorName = author.getFirstName() + " " + author.getLastName();
			nameList.add(authorName);
		}
		String authorNames = nameList.toString().replace("[", "").replace("]", "");
		return authorNames;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", postTitle=" + postTitle + ", date=" + date + ", postContent=" + postContent
				+ ", authors=" + getAuthorNames() + ", genre=" + genre + "]";
	}

}
