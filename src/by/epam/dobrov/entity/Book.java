package by.epam.dobrov.entity;
// Generated 06.05.2020 11:19:20 by Hibernate Tools 5.2.12.Final

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "book", catalog = "book_shop", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
@NamedQueries({ @NamedQuery(name = "Book.findAll", query = "Select b from Book b order by b.title"), // order by -
																										// сортировать
																										// по
		@NamedQuery(name = "Book.findByTitle", query = "Select b from Book b where b.title= :title"),
		@NamedQuery(name = "Book.findByIsbn", query = "Select b from Book b where b.isbn= :isbn"),
		@NamedQuery(name = "Book.countAll", query = "Select COUNT(*) from Book b "),
		@NamedQuery(name = "Book.findByCategory", query = "Select b from Book b join fetch "
				+ "Category c ON b.category.categoryId = c.categoryId AND c.categoryId = :сatId"),

		/*
		 * //% - для соответствия нулю или более символов. || - конкатенация с кейвордом
		 * в jpql
		 */
		@NamedQuery(name = "Book.searchBook", query = "Select b from Book b where b.title like '%' || :keyword || '%' "
				+ "or b.author like '%' || :keyword || '%' or b.description like '%' || :keyword || '%' "),
		/*
		 * or b.author like :keyword or b.description like :keyword // используем join
		 * on(Выбираются только те записи, которые совпадают в обоих таблицах.) чтобы
		 * соединить 2 таблицы, бук и категори выбрать книги из табл книги и категории ,
		 * где категорииИД книг = категориямИд и категорииИД = вызванному № категорииИД
		 */

})
public class Book implements java.io.Serializable {

	private Integer bookId;
	private Category category;
	private String title;
	private String author;
	private String description;
	private String isbn;
	private byte[] image;
	private String base64Image;
	private float price;
	private Set<OrderDetails> orderDetailses = new HashSet<OrderDetails>(0);

	public Book() {
	}

	public Book(Category category, String title, String author, String description, String isbn, byte[] image,
			float price) {
		this.category = category;
		this.title = title;
		this.author = author;
		this.description = description;
		this.isbn = isbn;
		this.image = image;
		this.price = price;

	}

	public Book(Category category, String title, String author, String description, String isbn, String base64Image,
			float price) {
		super();
		this.category = category;
		this.title = title;
		this.author = author;
		this.description = description;
		this.isbn = isbn;
		this.base64Image = base64Image;
		this.price = price;
	}

	public Book(Category category, String title, String author, String description, String isbn, byte[] image,
			float price, Set<OrderDetails> orderDetailses) {
		this.category = category;
		this.title = title;
		this.author = author;
		this.description = description;
		this.isbn = isbn;
		this.image = image;
		this.price = price;
		this.orderDetailses = orderDetailses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "book_id", unique = true, nullable = false)
	public Integer getBookId() {

		return this.bookId;

	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId", nullable = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name = "title", unique = true, nullable = false, length = 128)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "author", nullable = false, length = 45)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "description", nullable = false, length = 16777215)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "isbn", nullable = false, length = 45)
	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Column(name = "image", nullable = false)
	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Column(name = "price", nullable = false, precision = 12, scale = 0)
	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
	public Set<OrderDetails> getOrderDetailses() {
		return this.orderDetailses;
	}

	public void setOrderDetailses(Set<OrderDetails> orderDetailses) {
		this.orderDetailses = orderDetailses;
	}

	@Transient // имеется ввиду что геттер и сеттер не связаны ни с одним полем из БД
	public String getBase64Image() {
		this.base64Image = Base64.getEncoder().encodeToString(this.image);
		return this.base64Image;
		/*
		 * Если хотим напрямую закодировать строку и получить результат в виде
		 * закодированной строки, можем использовать это.
		 */
	}

	@Transient
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

}
