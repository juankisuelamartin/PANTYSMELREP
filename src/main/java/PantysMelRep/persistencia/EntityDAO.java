package PantysMelRep.persistencia;

public abstract class EntityDAO<E> {

	/**
	 * 
	 * @param id
	 */
	public abstract E select(String id);

	/**
	 * 
	 * @param entity
	 */
	public abstract int insert(E entity);

	/**
	 * 
	 * @param entity
	 */
	public abstract int update(E entity);

	/**
	 * 
	 * @param entity
	 */
	public abstract int delete(E entity);

}