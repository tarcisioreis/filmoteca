package com.filmoteca.app.dao.iDao;

import com.filmoteca.app.beans.AbstractBean;

import java.util.List;

/**
 *
 *
 * Classe Interface para implementar de metodos basicos para manipulacao de dados em banco de dados
 * 
 *
 */
public interface iBD {

	int update(AbstractBean bean);
	long insert(AbstractBean bean);
	int delete(AbstractBean bean);
	int delete();
	List<AbstractBean> search() throws Exception;
    List<AbstractBean> search(int id) throws Exception;

}
