package br.com.rfa.repository;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.rfa.model.UsuarioModel;
import br.com.rfa.repository.entity.UsuarioEntity;
import br.com.rfa.uteis.ConvertPasswordToMD5;
import br.com.rfa.uteis.Uteis;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	UsuarioEntity usuarioEntity;

	EntityManager entityManager;
	
	/***
	 * MÉTODO RESPONSÁVEL POR SALVAR UM NOVO USUÁRIO
	 * @param usuarioModel
	 */
	public void SalvarNovoRegistro(UsuarioModel usuarioModel){

		entityManager =  Uteis.JpaEntityManager();

		usuarioEntity = new UsuarioEntity();
		usuarioEntity.setUsuario(usuarioModel.getUsuario());
		
		String psw;
		try {
			psw = ConvertPasswordToMD5.convertPasswordToMD5(usuarioModel.getSenha());
		
			usuarioEntity.setSenha(psw);
			entityManager.persist(usuarioEntity);			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			
			Uteis.Mensagem("Erro ao converter senha!");
			e.printStackTrace();
		}

	}	
	
	public UsuarioEntity ValidaUsuario(UsuarioModel usuarioModel){

		try {
			//QUERY QUE VAI SER EXECUTADA (UsuarioEntity.findUser) 	
			Query query = Uteis.JpaEntityManager().createNamedQuery("UsuarioEntity.findUser");
			//PARÂMETROS DA QUERY
			String psw = ConvertPasswordToMD5.convertPasswordToMD5(usuarioModel.getSenha());
			query.setParameter("usuario", usuarioModel.getUsuario());
			query.setParameter("senha", psw);
			//RETORNA O USUÁRIO SE FOR LOCALIZADO
			return (UsuarioEntity)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}