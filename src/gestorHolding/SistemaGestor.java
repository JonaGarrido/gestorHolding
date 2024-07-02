package gestorHolding;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SistemaGestor implements Serializable {
    private ArrayList<Usuario> usuarios;
    private ArrayList<Empresa> empresas;
    private ArrayList<Area> areas;
    private ArrayList<Pais> paises;

    public SistemaGestor() {
        usuarios = new ArrayList<Usuario>();
        empresas = new ArrayList<Empresa>();
        areas = new ArrayList<Area>();
        paises = new ArrayList<Pais>();
    }

    
    /** 
     * @param a
     * @return SistemaGestor
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public SistemaGestor deSerializar(String a) throws IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream(a);
        ObjectInputStream o = new ObjectInputStream(f);
        SistemaGestor s = (SistemaGestor) o.readObject();
        o.close();
        f.close();
        return s;
    }

    public void serializar(String a) throws IOException {
        FileOutputStream f = new FileOutputStream(a);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(this);
        o.close();
        f.close();
    }

    public Usuario buscarUsuario(String datos) {
        boolean encontrado = false;
        int i = 0;
        Usuario usuario = null;
        while (!encontrado&&i<this.usuarios.size()) {
            usuario = usuarios.get(i);
            if (usuario.coincidenUsrPwd(datos))
                encontrado = true;
            else
                i++;
        }
        if (!encontrado) {
            return null;
        } else {
            return usuario;
        }
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void eliminarUsuario(Usuario usuarioAElminiar) {
        usuarios.remove(usuarioAElminiar);
    }

    public ArrayList<Empresa> getEmpresas() {
        return empresas;

    }

    public void eliminarEmpresa(Empresa empresaAElminiar) {
        empresas.remove(empresaAElminiar);
    }

    public ArrayList<Pais> getPaises() {
        return paises;

    }

    public void eliminarPais(Pais paisAElminiar) {
        paises.remove(paisAElminiar);
    }

    public ArrayList<Area> getAreas() {
        return areas;
    }

    public void eliminarArea(Area areaAElminiar) {
        areas.remove(areaAElminiar);
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void agregarEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
    }

    public void agregarArea(Area area) {
        this.areas.add(area);
    }

    public void agregarPais(Pais pais) {
        this.paises.add(pais);
    }
}
