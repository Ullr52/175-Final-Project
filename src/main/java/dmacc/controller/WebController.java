package dmacc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;





import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String viewAllComicsBooks(Model model) {
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
		return viewAllComics(model);
	}
	
	@GetMapping("/editComic/{id}")
	public String showUpdateComic(@PathVariable("id") long id, Model model) {
		ComicBookInformation c = repo.findById(id).orElse(null);
		model.addAttribute("newComic", c);
		return "input-comic-information";
	}
	
	@RequestMapping(value = "/update/{id}")
	public String revisedComic(ComicBookInformation c, Model model) {
		repo.save(c);
		return viewAllComics(model);
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String deleteComic(@PathVariable("id") long id, Model model) {
		ComicBookInformation c = repo.findById(id).orElse(null);
		List<UserInformation> uI = repo2.findAll();
		for(UserInformation u : uI) {
			List<ComicBookInformation> selectedComics = u.getSelectedComics();
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
		return addNewComicBook(model);
	}
	
	@GetMapping("/result-comic-list")
	
	public String viewAllComics(Model model) {
		if(repo.findAll().isEmpty()) {
			return viewAllComics(model);
		}
		model.addAttribute("newComics", repo.findAll());
		return "result-comic-list";
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
		return "currentuser";
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		UserInformation u = repo2.findById(id).orElse(null);
		repo2.delete(u);
		return viewAllUsers(model);
	}
	
	@RequestMapping(value = "/result-user-list", method = {RequestMethod.POST, RequestMethod.GET})
	public String viewAllUsers(Model model) {
		if(repo2.findAll().isEmpty()) {
			return viewAllUsers(model);
		}
		model.addAttribute("newUsers", repo2.findAll());
		return "result-user-list";
	}
	@GetMapping("/addComicSelected/{id}")
	public String selectedComic(@PathVariable("id") long id, Model model) {
		UserInformation u = repo2.findById(id).orElse(null);
		model.addAttribute("currentUser", u);
		model.addAttribute("comics", repo.findAll());
		return "addSelectedComic";
	}
	
	@GetMapping("/addComicsSelectedToList/{id}/{userId}")
	public String addSelectedComic(@PathVariable("id") long id, @PathVariable("userId") long userId, Model model) {
		ComicBookInformation c = repo.findById(id).orElse(null);
		UserInformation u = repo2.findById(id).orElse(null);
		List<ComicBookInformation> selectedComics = u.getSelectedComics();
		if(!selectedComics.contains(c)) {
			selectedComics.add(c);
		}
		u.setSelectedComics(selectedComics);
		repo2.save(u);
		model.addAttribute("currentUser", u);
		return "currentuser";
	}
	
	@GetMapping("/deleteComicsSelected/{id}/{userId}")
	public String deleteSelectedComic(@PathVariable("id") long id, @PathVariable("userId") long userId, Model model) {
		UserInformation u = repo2.findById(userId).orElse(null);
		List<ComicBookInformation> selectedComics = u.getSelectedComics();
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
		return "currentuser";
		
	}
	@GetMapping("/getKeywordResults")
	public String getSearchResults(String keyword, Model model) {
		return "input-keyword";
	}
	
	@PostMapping("/keywordSearch")
	public String keywordSearch(String keyword, Model model) {
		List<ComicBookInformation> c = repo.searchComicBookInformationi(keyword);
		model.addAttribute("comicSearch", c);
		if (c.isEmpty()) {
			System.out.println("No Comics Found");
			return "input-keyword";
		}
		System.out.println(repo.searchComicBookInformationi(keyword));
		return "keyword-results";
	}
}