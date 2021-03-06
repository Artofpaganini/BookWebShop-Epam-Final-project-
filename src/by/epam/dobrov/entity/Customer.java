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
@Table(name = "customer", catalog = "book_shop", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NamedQueries({ @NamedQuery(name = "Customer.findAll", query = "Select cs from Customer cs order by cs.fullName"),
		@NamedQuery(name = "Customer.findByEmail", query = "Select cs from Customer cs where cs.email= :email"),
		@NamedQuery(name = "Customer.countAll", query = "Select COUNT(*) from Customer cs "),
		@NamedQuery(name = "Customer.checkLogin", query = "Select cs from Customer cs where cs.email = :email and cs.password = :password") })
public class Customer implements java.io.Serializable {

	private Integer customerId;
	private String email;
	private String fullName;
	private String address;
	private String city;
	private String country;
	private String phone;
	private String zipCode;
	private String password;

	private float customerBalance = (float) (Math.random() * (300 - 1)) + 1;

	private boolean block = false;

	private Set<BookOrder> orders = new HashSet<BookOrder>(0);

	public Customer() {
	}

	public Customer(String email, String fullName, String address, String city, String country, String phone,
			String zipCode, String password) {
		this.email = email;
		this.fullName = fullName;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.zipCode = zipCode;
		this.password = password;

	}

	public Customer(String email, String fullName, String address, String city, String country, String phone,
			String zipCode, String password, Set<BookOrder> orders) {
		this.email = email;
		this.fullName = fullName;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.zipCode = zipCode;
		this.password = password;
		this.orders = orders;
	}

	public Customer(String email, String fullName, String address, String city, String country, String phone,
			String zipCode, String password, boolean block) {
		super();
		this.email = email;
		this.fullName = fullName;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.zipCode = zipCode;
		this.password = password;
		this.block = block;

	}

	public Customer(Integer customerId) {
		super();
		this.customerId = customerId;
	}

	public Customer(Integer customerId, String email, String fullName, String address, String city, String country,
			String phone, String zipCode, String password, boolean block, Set<BookOrder> orders) {
		super();
		this.customerId = customerId;
		this.email = email;
		this.fullName = fullName;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.zipCode = zipCode;
		this.password = password;
		this.block = block;
		this.orders = orders;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "customer_id", unique = true, nullable = false)
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "email", unique = true, nullable = false, length = 128)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "full_name", nullable = false, length = 64)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "address", nullable = false, length = 128)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "city", nullable = false, length = 45)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "country", nullable = false, length = 45)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "phone", nullable = false, length = 45)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "zip_code", nullable = false, length = 45)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "password", nullable = false, length = 45)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "block", nullable = false)
	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
	public Set<BookOrder> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<BookOrder> orders) {
		this.orders = orders;
	}

	@Column(name = "customer_balance", nullable = false, precision = 12, scale = 0)
	public float getCustomerBalance() {
		return customerBalance;
	}

	public void setCustomerBalance(float customerBalance) {
		this.customerBalance = customerBalance;
	}
}
