package br.com.rfa.pessoa.controller;



import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import br.com.rfa.model.UsuarioModel;
import br.com.rfa.repository.UsuarioRepository;
import br.com.rfa.uteis.Uteis;

@Named(value="cadastrarUsuarioController")
@RequestScoped
public class CadastrarUsuarioController {

	@Inject
	UsuarioModel usuarioModel;

//	@Inject
//	UsuarioController usuarioController;

	@Inject
	UsuarioRepository usuarioRepository;

	private UploadedFile file;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}	
	
	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	/**
	 *SALVA UM NOVO REGISTRO VIA INPUT 
	 */
	public void SalvarNovoUsuario(){
//		usuarioModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());
	//INFORMANDO QUE O CADASTRO FOI VIA INPUT
		//pessoaModel.setOrigemCadastro("I");
		usuarioRepository.SalvarNovoRegistro(this.usuarioModel);
		this.usuarioModel = null;

		Uteis.MensagemInfo("Registro cadastrado com sucesso");
	}
	
}
