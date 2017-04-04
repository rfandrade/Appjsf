package br.com.rfa.usuario.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.rfa.model.UsuarioModel;
import br.com.rfa.repository.UsuarioRepository;
import br.com.rfa.repository.entity.UsuarioEntity;
import br.com.rfa.uteis.Uteis;

@Named(value="usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioModel usuarioModel;

	@Inject
	private UsuarioRepository usuarioRepository;

	@Inject
	private UsuarioEntity usuarioEntity;

	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}
	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	// Recupera o objeto que representa o usu�rio logado e autenticado pelo sistema, este objeto estar� setado no contexto da sistema
	public UsuarioModel GetUsuarioSession(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return (UsuarioModel)facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}

	// Efetua logout redirencionando para a pagina inicial (index.xhtml)
	public String Logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}
	
	/* Efetua login seguindo os seguintes passo:
		1- verifiando se usu�rio e senha foram informados, se n�o informa ao usu�rio e retorna null (volta para tela incial sem logar).
		2- valida usu�rio, buscando na base de dados usu�rio cadastrado conforme o informado pelo usu�rio, se n�o encontrado avisa o usu�rio e retorna null (volta para tela incial sem logar). 
		3- carrega o objeto usuarioModel com os dados encontrados na base de dados.
		4- seta no contexto do sistema  a vari�vel denominada "usuarioAutenticado" com o objeto "usuarioModel"
		5- redireciona a requisi��o para a pagina "home" (home.xhtml). 
	*/
	public String EfetuarLogin(){
		if(StringUtils.isEmpty(usuarioModel.getUsuario()) || StringUtils.isBlank(usuarioModel.getUsuario())){
			Uteis.Mensagem("Favor informar o login!");
			return null;
		}
		else if(StringUtils.isEmpty(usuarioModel.getSenha()) || StringUtils.isBlank(usuarioModel.getSenha())){
			Uteis.Mensagem("Favor informar a senha!");
			return null;
		}
		else{	
			usuarioEntity = usuarioRepository.ValidaUsuario(usuarioModel);
			if(usuarioEntity!= null){
				usuarioModel.setSenha(null);
				usuarioModel.setCodigo(usuarioEntity.getCodigo());

				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioModel);

				return "sistema/home?faces-redirect=true";
			}
			else{
				Uteis.Mensagem("N�o foi poss�vel efetuar o login com esse usu�rio e senha!");
				return null;
			}
		}
	}
}