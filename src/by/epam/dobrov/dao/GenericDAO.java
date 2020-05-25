package by.epam.dobrov.dao;

import java.util.List;

public interface GenericDAO<E> {


	
	public E create(E t);

	public E update(E t);

	public E get(Object id);

	public void delete(Object id);

//	public E block(E t);

	public List<E> listAll();

	long count();
}