package dmacc.beans;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jword - jord
 * CIS175 - Spring - 2022
 * Apr 11, 2022
 */
@Data
@Entity
@NoArgsConstructor
public class ComicBookInformation {
	@Id
	@GeneratedValue
	private long id;
	private String publisher;
	private String seriesTitle;
	private String issueNum;
	private String author;
	private String illustrator;
	private String yearPub;
	private String comicImage;
	/**
	 * @param publisher
	 * @param seriesTitle
	 * @param issueNum
	 * @param author
	 * @param illustrator
	 */
	public ComicBookInformation(String publisher, String seriesTitle, String issueNum, String author,
			String illustrator, String yearPub) {
		super();
		this.publisher = publisher;
		this.seriesTitle = seriesTitle;
		this.issueNum = issueNum;
		this.author = author;
		this.illustrator = illustrator;
		this.yearPub = yearPub;
	}
	/**
	 * @param id
	 * @param publisher
	 * @param seriesTitle
	 * @param issueNum
	 * @param author
	 * @param illustrator
	 * @param yearPub
	 * @param comicImage
	 */
	public ComicBookInformation(long id, String publisher, String seriesTitle, String issueNum, String author,
			String illustrator, String yearPub, String comicImage) {
		super();
		this.id = id;
		this.publisher = publisher;
		this.seriesTitle = seriesTitle;
		this.issueNum = issueNum;
		this.author = author;
		this.illustrator = illustrator;
		this.yearPub = yearPub;
		this.comicImage = comicImage;
	}
	
	
	
	

}
