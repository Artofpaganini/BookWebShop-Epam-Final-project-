package by.epam.dobrov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "users", catalog = "book_shop", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NamedQueries({ @NamedQuery(name = "Users.findAll", query = "Select u from Users u order by u.fullName"),
		@NamedQuery(name = "Users.findByEmail", query = "Select u from Users u where u.email= :email"),
		@NamedQuery(name = "Users.countAll", query = "Select COUNT(*) from Users u "),
		@NamedQuery(name = "Users.checkLogin", query = "Select u from Users u where u.email =:email and u.password =:password") })
public class Users implements java.io.Serializable {

	private Integer usersId;

	private String email;
	private String password;
	private String fullName;

	public Users() {
	}

	public Users(String email, String fullName, String password) {
		this.email = email;
		this.fullName = fullName;
		this.password = password;

	}

	public Users(Integer usersId, String email, String password, String fullName) {

		this.usersId = usersId;
		this.email = email;
		this.password = password;
		this.fullName = fullName;

	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "users_id", unique = true, nullable = false)
	public Integer getUsersId() {
		return this.usersId;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	@Column(name = "email", unique = true, nullable = false, length = 45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", nullable = false, length = 45)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "full_name", nullable = false, length = 45)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
