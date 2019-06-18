package br.luiz.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import br.luiz.entity.Perfil;
import br.luiz.entity.Usuario;

@ManagedBean
//@Stateless
//@RequestScoped
@SessionScoped
//@ViewScoped
//@Named (value="usuarioMB")
public class UsuarioMB  implements Serializable {
	
	private static final long serialVersionUID = 2570045328684716449L;
	
	@PersistenceContext(unitName = "UsuarioPU")
	private EntityManager EM;
		
	private Usuario                      usuario;	
	private List<Usuario>                usuarios;
	private Perfil                       perfil;
	private List<Perfil>                 perfis;
	
		
	public EntityManager getEM() {
        return EM;
    }
		
	@PostConstruct
	public void init() {			
		usuario                        = new Usuario();
		usuarios                       = new ArrayList<Usuario>();
		perfil                         = new Perfil();
		perfis                         = new ArrayList<Perfil>();
	}
	
 	public UsuarioMB() {
		super();		
	}
 	
 	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getPerfis() {
		this.perfis = buscarPerfis();
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
 	
 	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {		
		usuarios = buscarUsuarios();	
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public List<Perfil> buscarPerfis(){
		TypedQuery<Perfil> result = EM.createQuery("SELECT per FROM Perfil per", Perfil.class);						
		return result.getResultList();
	}		
	 	 			
	public List<Usuario> buscarUsuarios(){
		TypedQuery<Usuario> consulta = EM.createQuery("SELECT usu FROM Usuario usu", Usuario.class);						
		return consulta.getResultList();
	}		
	     	
	public void salvar(){				
		//EM.persist(this.usuario);		
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuario Salvo com Sucesso !"));
	    //return "index?faces-redirect=true";
	}		
	
	public String excluir(int id){				
	    Usuario usu = EM.find(Usuario.class, id);
	    EM.remove(usu);
	    EM.flush();			
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuario Removido com Sucesso !"));
	    return "index?faces-redirect=true";
	}		
	
	public String editar(int id){				
	    Usuario usu = EM.find(Usuario.class, id);
	    this.usuario = usu;
	    return "edit?faces-redirect=true";
	    	    
	}		
}