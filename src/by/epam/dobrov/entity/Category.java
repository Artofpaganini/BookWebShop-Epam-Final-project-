package by.epam.dobrov.entity;
// Generated 06.05.2020 11:19:20 by Hibernate Tools 5.2.12.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 9. ������� ��������-�������. ������������� ������������ ������� ��������
 * �������. ������ ������ � ���������� ����� �� ������. ������������� �����
 * ������� �������������� � ������� ������.
 * 
 * @author Viktor
 *
 */
@Entity
@Table(name = "category", catalog = "book_shop", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NamedQueries({ @NamedQuery(name = "Category.findAll", query = "Select c from Category c order by c.name"),
		@NamedQuery(name = "Category.countAll", query = "Select COUNT(*) from Category c "),
		@NamedQuery(name = "Category.findByName", query = "Select c from Category c where c.name = :name") })
public class Category implements java.io.Serializable {

	private Integer categoryId;
	private String name;
	private Set<Book> books = new HashSet<Book>(0);

	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	public Category(Integer categoryId, String name) {

		this.categoryId = categoryId;
		this.name = name;
	}

	public Category(String name, Set<Book> books) {
		this.name = name;
		this.books = books;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "categoryId", unique = true, nullable = false)
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "name", unique = true, nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Book> getBooks() {
		return this.books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

}
