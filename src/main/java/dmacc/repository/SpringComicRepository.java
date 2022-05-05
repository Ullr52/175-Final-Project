package dmacc.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dmacc.beans.ComicBookInformation;




/**
 * @author jword - jord
 * CIS175 - Spring - 2022
 * Apr 11, 2022
 */
@Repository
public interface SpringComicRepository extends JpaRepository<ComicBookInformation, Long> {



List<ComicBookInformation> findByPublisher(String publisher);


//Custom query
@Query("SELECT c FROM ComicBookInformation c WHERE CONCAT(c.publisher, ' ', c.seriesTitle, ' ', c.issueNum, ' ', c.author, ' ', c.illustrator, ' ', c.yearPub) LIKE %?1%")
	public List<ComicBookInformation> searchComicBookInformation(String keyword);
}
