package gestorHolding;

import java.io.Serializable;

public abstract class Usuario implements Serializable, Mostrable {

    protected String usuario;
    protected String password;

    
    /** 
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public abstract boolean proceder(SistemaGestor sistemaGestor);

    public abstract void mostrar();

    public abstract Usuario accept(Visitor v);

    public boolean coincidenUsrPwd(String datos) {
        return datos.equals(this.usuario + ":" + this.password);
    }
}
