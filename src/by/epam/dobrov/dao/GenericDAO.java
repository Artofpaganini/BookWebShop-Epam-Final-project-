package by.epam.dobrov.dao;

import java.util.List;

/**
 * 9. ������� ��������-�������. ������������� ������������ ������� ��������
 * �������. ������ ������ � ���������� ����� �� ������. ������������� �����
 * ������� �������������� � ������� ������.
 * 
 * @author Viktor
 *
 */
public interface GenericDAO<E> {

	public E create(E t);

	public E update(E t);

	public E get(Object id);

	public void delete(Object id);

	public List<E> listAll();

	long count();
}
