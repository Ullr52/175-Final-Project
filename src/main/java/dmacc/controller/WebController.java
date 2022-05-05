package dmacc.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;





import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import dmacc.beans.ComicBookInformation;
import dmacc.beans.UserInformation;
import dmacc.repository.SpringComicRepository;
import dmacc.repository.SpringUserRepository;

/**
 * @author jword - jord
 * CIS175 - Spring - 2022
 * Apr 11, 2022
 */
@Controller
public class WebController {
	
	@Autowired
	SpringComicRepository repo;
	
	@Autowired
	SpringUserRepository repo2;
	
	
	
	@GetMapping("/viewAllComics")
	public String viewAllComicBooks(Model model) {
		model.addAttribute("comics", repo.findAll());
		System.out.println(repo.findAll());
		return "result-comic-list";
	}

	@GetMapping("/input-comic-information")
	public String addNewComicBook(Model model) {
		ComicBookInformation c = new ComicBookInformation();
		model.addAttribute("newComic", c);
		return "input-comic-information";
	}
	
	@PostMapping("/input-comic-information")
	public String addNewComic(@ModelAttribute ComicBookInformation c, Model model) {
		repo.save(c);
		return viewAllComicBooks(model);
	}
	
	@GetMapping("/editComic/{id}")
	public String showUpdateComic(@PathVariable("id") long id, Model model) {
		ComicBookInformation c = repo.findById(id).orElse(null);
		model.addAttribute("newComic", c);
		return "input-comic-information";
	}
	
	@PostMapping("/update/{id}")
	public String revisedComic(ComicBookInformation c, Model model) {
		repo.save(c);
		return viewAllComicBooks(model);
	}
	
	@GetMapping("/delete/{id}")
	public String deleteComic(@PathVariable("id") long id, Model model) {
		ComicBookInformation c = repo.findById(id).orElse(null);
		List<UserInformation> users = repo2.findAll();
		for(UserInformation u : users) {
			List <ComicBookInformation> selectedComics = u.getSelectedComics();
			int indexToDelete = -1;
			for (int i = 0; i < selectedComics.size(); i++) {
				if (selectedComics.get(i).getId() == id) {
					indexToDelete = i;
				}
			}
			if (indexToDelete != -1) {
				selectedComics.remove(indexToDelete);
				u.setSelectedComics(selectedComics);
			}
		}
		repo.delete(c);
		return viewAllComicBooks(model);
	}
	
	
	
	@GetMapping("/input-user-information")
	public String addNewUser(Model model) {
		UserInformation u = new UserInformation();
		model.addAttribute("newUser", u);
		return "input-user-information";
	}
	
	@PostMapping("/input-user-information")
	public String addNewUser(@ModelAttribute UserInformation u, Model model) {
		repo2.save(u);
		return viewAllUsers(model);
	}
	
	@GetMapping("/editUser/{id}")
	public String showUpdateUser(@PathVariable("id") long id, Model model) {
		UserInformation u = repo2.findById(id).orElse(null);
		model.addAttribute("newUser", u);
		return "input-user-information";
	}
	
	
	@PostMapping("/updateUser/{id}")
	public String revisedUser(UserInformation u, Model model) {
		repo2.save(u);
		return viewAllUsers(model);
	}
	
	@GetMapping("/displayUser/{id}")
	public String displaySelectedComics(@PathVariable("id") long id, Model model) {
		UserInformation u = repo2.findById(id).orElse(null);
		model.addAttribute("currentUser", u);
		return "display-user";
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		UserInformation u = repo2.findById(id).orElse(null);
		repo2.delete(u);
		return viewAllUsers(model);
	}
	
	@GetMapping("/viewAllUsers")
	public String viewAllUsers(Model model) {
		model.addAttribute("users", repo2.findAll());
		return "result-user-list";
	}
	@GetMapping("/addComic/{id}")
	public String selectComic(@PathVariable("id") long id, Model model) {
		UserInformation u = repo2.findById(id).orElse(null);
		model.addAttribute("currentUser", u);
		model.addAttribute("comics", repo.findAll());
		return "comic-selected";
	}
	
	@GetMapping("/addComicsSelectedToList/{id}/{userId}")
	public String addSelectedComics(@PathVariable("id") long id, @PathVariable("userId") long userId, Model model) {
		ComicBookInformation c = repo.findById(id).orElse(null);
		UserInformation u = repo2.findById(userId).orElse(null);
		List <ComicBookInformation> selectedComics = u.getSelectedComics();
		if(!selectedComics.contains(c)) {
			selectedComics.add(c);
		}
		u.setSelectedComics(selectedComics);
		repo2.save(u);
		model.addAttribute("currentUser", u);
		return "display-user";
	}
	
	@GetMapping("/deleteComicSelected/{id}/{userId}")
	public String deleteSelectedComic(@PathVariable("id") long id, @PathVariable("userId") long userId, Model model) {
		UserInformation u = repo2.findById(userId).orElse(null);
		List <ComicBookInformation> selectedComics = u.getSelectedComics();
		int indexToDelete = 0;
		for (int i = 0; i < selectedComics.size(); i++) {
			if (selectedComics.get(i).getId() == id) {
				indexToDelete = i;
			}
		}
		selectedComics.remove(indexToDelete);
		u.setSelectedComics(selectedComics);
		repo2.save(u);
		model.addAttribute("currentUser", u);
		return "display-user";
		
	}
	@GetMapping("/getKeywordResults")
	public String getKeywordResults(String keyword, Model model) {
		return "keyword-search";
	}
	
	@PostMapping("/keywordSearch")
	public String keywordSearch(String keyword, Model model) {
		List<ComicBookInformation> c = repo.searchComicBookInformation(keyword);
		model.addAttribute("comicsSearch", c);
		if (c.isEmpty()) {
			System.out.println("No Comics Found");
			return "keyword-search";
		}
		System.out.println(repo.searchComicBookInformation(keyword));
		return "keyword-results";
	}
}