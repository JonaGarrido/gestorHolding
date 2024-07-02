package gestorHolding;

import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Administrador extends Usuario {

    public Administrador(String usuario, String password) {
        setUsuario(usuario);
        setPassword(password);
    }

    
    /** 
     * @param sistemaGestor
     * @return boolean
     */
    @Override
    public boolean proceder(SistemaGestor sistemaGestor) {
        int opcion, opcion2;
        boolean volver;
        do {
            volver = false;
            do {
                EntradaSalida.mostrarString("");
                EntradaSalida.mostrarString("Bienvenido/a al sistema, Sr(a) Administrador(a)");
                EntradaSalida.mostrarString("1- Agregar");
                EntradaSalida.mostrarString("2- Eliminar");
                EntradaSalida.mostrarString("3- Mostrar datos cargados");
                EntradaSalida.mostrarString("4- Salir");
                opcion = EntradaSalida.leerInt("Ingrese la opcion deseada: ");
            } while (opcion < 1 || opcion > 4);
            if (opcion != 3 && opcion != 4) {
                do {
                    EntradaSalida.mostrarString("");
                    EntradaSalida.mostrarString("1- Vendedor");
                    EntradaSalida.mostrarString("2- Asesor");
                    EntradaSalida.mostrarString("3- Asesoria");
                    EntradaSalida.mostrarString("4- Administrador");
                    EntradaSalida.mostrarString("5- Empresa");
                    EntradaSalida.mostrarString("6- Area");
                    EntradaSalida.mostrarString("7- Area a Empresa");
                    EntradaSalida.mostrarString("8- Pais");
                    EntradaSalida.mostrarString("9- Pais a Empresa");
                    EntradaSalida.mostrarString("10- Volver");
                    opcion2 = EntradaSalida.leerInt("Ingrese la opcion deseada: ");
                } while (opcion < 1 || opcion > 10);
                volver = true;
                if (opcion2 != 10) {
                    operar(opcion, opcion2, sistemaGestor);
                    try {
                        sistemaGestor.serializar("holding.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (opcion == 3) {
                EntradaSalida.mostrarString("");
                EntradaSalida.mostrarString("DATOS CARGADOS: ");
                EntradaSalida.mostrarString("");
                EntradaSalida.mostrarString("USUARIOS");
                for (Usuario usuario : sistemaGestor.getUsuarios())
                    usuario.mostrar();
                EntradaSalida.mostrarString("PAISES");
                for (Pais pais : sistemaGestor.getPaises())
                    pais.mostrar();
                EntradaSalida.mostrarString("AREAS");
                for (Area area : sistemaGestor.getAreas())
                    area.mostrar();
                EntradaSalida.mostrarString("EMPRESAS");
                for (Empresa empresa : sistemaGestor.getEmpresas())
                    empresa.mostrar();
                volver = true;
            }
        } while (volver);
        return true;
    }

    private void operar(int operacion, int entidad, SistemaGestor sistemaGestor) {
        ArrayList<Usuario> usuarios = sistemaGestor.getUsuarios();
        if (operacion == 1) {
            if (entidad == 1) {
                ArrayList<Empresa> empresas = sistemaGestor.getEmpresas();
                if (empresas.size() == 0)
                    EntradaSalida.mostrarString("NO HAY EMPRESAS AGREGADAS, PRIMERO AGREGAR UNA EMPRESA");
                else {
                    ArrayList<Vendedor> vendedores = new ArrayList<Vendedor>();
                    Visitor v = new obtenerV();
                    for (Usuario usuario : usuarios) {
                        if (v.visit(usuario) != null)
                            vendedores.add((Vendedor) usuario);
                    }

                    String usVendedor = EntradaSalida.leerString("ALTA DE VENDEDOR\nIngrese el usuario: ");
                    if (usVendedor.equals(""))
                        EntradaSalida.mostrarString("ERROR: usuario no valido");
                    else {
                        String paVendedor = EntradaSalida.leerString("Ingrese el password:");
                        if (paVendedor.equals(""))
                            EntradaSalida.mostrarString("ERROR: password no valido");
                        else {
                            Vendedor vendedor = (Vendedor) sistemaGestor.buscarUsuario(usVendedor + ":" + paVendedor);
                            if (vendedor != null)
                                EntradaSalida.mostrarString("ERROR: El usuario ya figura en el sistema");
                            else {
                                String codVendedor = EntradaSalida.leerString("Ingrese el codigo:");
                                if (codVendedor.equals(""))
                                    EntradaSalida.mostrarString("ERROR: Codigo no valido");
                                else {
                                    boolean encontrado = false;
                                    int i = 0;
                                    while (!encontrado && i < vendedores.size()) {
                                        if (vendedores.get(i).mismoCodigo(codVendedor))
                                            encontrado = true;
                                        else
                                            i++;
                                    }
                                    if (encontrado)
                                        EntradaSalida.mostrarString("ERROR: El codigo se encuentra repetido");
                                    else {
                                        String nomVendedor = EntradaSalida.leerString("Ingrese el nombre:");
                                        if (nomVendedor.equals(""))
                                            EntradaSalida.mostrarString("ERROR: Nombre no valido");
                                        else {
                                            String dirVendedor = EntradaSalida.leerString("Ingrese una direccion:");
                                            if (dirVendedor.equals(""))
                                                EntradaSalida.mostrarString("ERROR: Direccion no valida");
                                            else {
                                                String codSupACargo = EntradaSalida.leerString(
                                                        "Ingrese el codigo de su vendedor superior a cargo (vacio sino tiene):");
                                                Vendedor vendedorSuperior = null;
                                                LocalDate fechaCaptacion = null;
                                                boolean superiorCorrecto = false;
                                                boolean vacio = false;
                                                if (!codSupACargo.equals("")) {
                                                    encontrado = false;
                                                    i = 0;
                                                    while (!encontrado && i < vendedores.size()) {
                                                        if (vendedores.get(i).mismoCodigo(codSupACargo))
                                                            encontrado = true;
                                                        else
                                                            i++;
                                                    }
                                                    if (!encontrado)
                                                        EntradaSalida.mostrarString(
                                                                "ERROR: No existe ese codigo de vendedor");

                                                    else {
                                                        vendedorSuperior = vendedores.get(i);
                                                        superiorCorrecto = true;
                                                        String fechaCaptacionSTR = EntradaSalida.leerString(
                                                                "Ingrese la fecha de captacion (DD/MM/AAAA):");
                                                        if (fechaCaptacionSTR.equals("")
                                                                || fechaCaptacionSTR.length() < 10)
                                                            EntradaSalida.mostrarString("ERROR: Fecha no valida");
                                                        else {
                                                            fechaCaptacion = LocalDate.parse(fechaCaptacionSTR,
                                                                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                                                        }
                                                    }
                                                } else {
                                                    superiorCorrecto = true;
                                                    vacio = true;
                                                }

                                                boolean crear = false;
                                                Empresa empVendedor = null;
                                                if (superiorCorrecto && vacio) {
                                                    String nomEmpVendedor = EntradaSalida
                                                            .leerString("Ingrese la empresa donde trabaja:");
                                                    encontrado = false;
                                                    i = 0;
                                                    while (!encontrado && i < empresas.size()) {
                                                        if (empresas.get(i).mismoNombre(nomEmpVendedor))
                                                            encontrado = true;
                                                        else
                                                            i++;
                                                    }
                                                    if (!encontrado)
                                                        EntradaSalida.mostrarString("ERROR: No existe la empresa");
                                                    else {
                                                        empVendedor = empresas.get(i);
                                                        crear = true;
                                                    }
                                                } else if (superiorCorrecto && !vacio) {
                                                    empVendedor = vendedorSuperior.getEmpresa();
                                                    crear = true;
                                                }

                                                if (crear) {
                                                    vendedor = new Vendedor(usVendedor, paVendedor, codVendedor,
                                                            nomVendedor, dirVendedor, empVendedor, vendedorSuperior,
                                                            fechaCaptacion);
                                                    sistemaGestor.agregarUsuario(vendedor);
                                                    empVendedor.sumarVendedor();
                                                    vendedor.agregarVendedorSuperior(vendedorSuperior);
                                                    if (vendedorSuperior != null)
                                                        vendedorSuperior.agregarVendedorACargo(vendedor);
                                                    EntradaSalida
                                                            .mostrarString("Se ha incorporado el vendedor al sistema");
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (entidad == 2) {
                ArrayList<Empresa> empresas = sistemaGestor.getEmpresas();
                ArrayList<Area> areas = sistemaGestor.getAreas();
                boolean continuar = true;
                if (empresas.size() == 0) {
                    EntradaSalida.mostrarString("NO HAY EMPRESAS AGREGADAS, PRIMERO AGREGAR UNA EMPRESA");
                    continuar = false;
                }
                if (areas.size() == 0) {
                    EntradaSalida.mostrarString("NO HAY AREAS AGREGADAS, PRIMERO AGREGAR UN AREA");
                    continuar = false;
                }
                if (continuar) {
                    ArrayList<Asesor> asesores = new ArrayList<Asesor>();
                    Visitor v = new obtenerAs();

                    for (Usuario usuario : usuarios) {
                        if (v.visit(usuario) != null)
                            asesores.add((Asesor) usuario);
                    }

                    String usAsesor = EntradaSalida.leerString("ALTA DE ASESOR\nIngrese el usuario: ");
                    if (usAsesor.equals(""))
                        EntradaSalida.mostrarString("ERROR: usuario no valido");
                    else {
                        String paAsesor = EntradaSalida.leerString("Ingrese el password:");
                        if (paAsesor.equals(""))
                            EntradaSalida.mostrarString("ERROR: password no valido");
                        else {
                            Asesor asesor = (Asesor) sistemaGestor.buscarUsuario(usAsesor + ":" + paAsesor);
                            if (asesor != null)
                                EntradaSalida.mostrarString("ERROR: El usuario ya figura en el sistema");
                            else {
                                String codAsesor = EntradaSalida.leerString("Ingrese el codigo:");
                                if (codAsesor.equals(""))
                                    EntradaSalida.mostrarString("ERROR: Codigo no valido");
                                else {
                                    boolean encontrado = false;
                                    int i = 0;
                                    while (!encontrado && i < asesores.size()) {
                                        if (asesores.get(i).mismoCodigo(codAsesor))
                                            encontrado = true;
                                        else
                                            i++;
                                    }
                                    if (encontrado)
                                        EntradaSalida.mostrarString("ERROR: El codigo se encuentra repetido");
                                    else {
                                        String nomAsesor = EntradaSalida.leerString("Ingrese el nombre:");
                                        if (nomAsesor.equals(""))
                                            EntradaSalida.mostrarString("ERROR: Nombre no valido");
                                        else {
                                            String dirAsesor = EntradaSalida.leerString("Ingrese una direccion:");
                                            if (dirAsesor.equals(""))
                                                EntradaSalida.mostrarString("ERROR: Direccion no valida");
                                            else {
                                                String titAsesor = EntradaSalida.leerString("Ingrese una titulacion:");

                                                if (titAsesor.equals(""))
                                                    EntradaSalida.mostrarString("ERROR: Titulacion no valida");
                                                else {
                                                    asesor = new Asesor(usAsesor, paAsesor, codAsesor, nomAsesor,
                                                            dirAsesor,
                                                            titAsesor);
                                                    sistemaGestor.agregarUsuario(asesor);
                                                    while (EntradaSalida
                                                            .leerBoolean("¿Quisiera ingresar una Asesoria?")) {
                                                        Asesoria asesoria;
                                                        String nomEmpresa = EntradaSalida
                                                                .leerString(
                                                                        "Ingrese el nombre de la empresa a asesorar:");
                                                        if (nomEmpresa.equals(""))
                                                            EntradaSalida.mostrarString("ERROR: Nombre no valido");
                                                        else {
                                                            encontrado = false;
                                                            i = 0;
                                                            while (!encontrado && i < empresas.size()) {
                                                                if (empresas.get(i).mismoNombre(nomEmpresa))
                                                                    encontrado = true;
                                                                else
                                                                    i++;
                                                            }
                                                            if (!encontrado)
                                                                EntradaSalida
                                                                        .mostrarString("ERROR: No existe la empresa");
                                                            else {
                                                                Empresa empAsesoria = empresas.get(i);
                                                                String nomArea = EntradaSalida
                                                                        .leerString("Ingrese el Area a asesorar:");
                                                                if (nomArea.equals(""))
                                                                    EntradaSalida
                                                                            .mostrarString("ERROR: Area no valida");
                                                                else {
                                                                    encontrado = false;
                                                                    i = 0;
                                                                    while (!encontrado && i < areas.size()) {
                                                                        if (areas.get(i).mismoNombre(nomArea))
                                                                            encontrado = true;
                                                                        else
                                                                            i++;
                                                                    }
                                                                    if (!encontrado)
                                                                        EntradaSalida
                                                                                .mostrarString(
                                                                                        "ERROR: La empresa no trabaja en esa area");
                                                                    else {
                                                                        Area areaAsesoria = areas.get(i);
                                                                        String fechaInicioSTR = EntradaSalida
                                                                                .leerString(
                                                                                        "Ingrese la fecha de inicio (DD/MM/AAAA):");
                                                                        if (fechaInicioSTR.equals("")
                                                                                || fechaInicioSTR.length() < 10)
                                                                            EntradaSalida.mostrarString(
                                                                                    "ERROR: Fecha no valida");
                                                                        else {
                                                                            LocalDate fechaInicio = LocalDate
                                                                                    .parse(fechaInicioSTR,
                                                                                            DateTimeFormatter
                                                                                                    .ofPattern(
                                                                                                            "dd/MM/yyyy"));
                                                                            asesoria = new Asesoria(empAsesoria,
                                                                                    areaAsesoria, fechaInicio);
                                                                            asesor.agregarAsesoria(asesoria);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    EntradaSalida
                                                            .mostrarString("Se ha incorporado un asesor al sistema");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (entidad == 3) {
                ArrayList<Empresa> empresas = sistemaGestor.getEmpresas();
                ArrayList<Area> areas = sistemaGestor.getAreas();
                boolean continuar = true;
                if (empresas.size() == 0) {
                    EntradaSalida.mostrarString("NO HAY EMPRESAS AGREGADAS, PRIMERO AGREGAR UNA EMPRESA");
                    continuar = false;
                }
                if (areas.size() == 0) {
                    EntradaSalida.mostrarString("NO HAY AREAS AGREGADAS, PRIMERO AGREGAR UN AREA");
                    continuar = false;
                }
                if (continuar) {
                    String codAsesor = EntradaSalida.leerString("AGREGAR ASESORIA\nIngrese el codigo de asesor: ");
                    if (codAsesor.equals(""))
                        EntradaSalida.mostrarString("ERROR: usuario no valido");
                    else {
                        ArrayList<Asesor> asesores = new ArrayList<Asesor>();
                        Visitor v = new obtenerAs();

                        for (Usuario usuario : usuarios) {
                            if (v.visit(usuario) != null)
                                asesores.add((Asesor) usuario);
                        }
                        boolean encontrado = false;
                        int i = 0;
                        while (!encontrado && i < asesores.size()) {
                            if (asesores.get(i).mismoCodigo(codAsesor))
                                encontrado = true;
                            else
                                i++;
                        }
                        if (!encontrado)
                            EntradaSalida
                                    .mostrarString("ERROR: El asesor no existe, agregarlo primero");
                        else {
                            Asesor asesor = asesores.get(i);
                            String nomEmpresa = EntradaSalida.leerString("Ingrese el nombre de la empresa a asesorar:");
                            if (nomEmpresa.equals(""))
                                EntradaSalida.mostrarString("ERROR: Nombre no valido");
                            else {
                                encontrado = false;
                                i = 0;
                                while (!encontrado && i < empresas.size()) {
                                    if (empresas.get(i).mismoNombre(nomEmpresa))
                                        encontrado = true;
                                    else
                                        i++;
                                }
                                if (!encontrado)
                                    EntradaSalida.mostrarString("ERROR: No existe la empresa");
                                else {
                                    Empresa empAsesoria = empresas.get(i);
                                    String nomArea = EntradaSalida
                                            .leerString("Ingrese el Area a asesorar:");
                                    if (nomArea.equals(""))
                                        EntradaSalida.mostrarString("ERROR: Area no valida");
                                    else {
                                        encontrado = false;
                                        i = 0;
                                        while (!encontrado && i < areas.size()) {
                                            if (areas.get(i).mismoNombre(nomArea))
                                                encontrado = true;
                                            else
                                                i++;
                                        }
                                        if (!encontrado)
                                            EntradaSalida
                                                    .mostrarString("ERROR: La empresa no trabaja en esa area");
                                        else {
                                            Area areaAsesoria = areas.get(i);
                                            String fechaInicioSTR = EntradaSalida.leerString(
                                                    "Ingrese la fecha de inicio (DD/MM/AAAA):");
                                            if (fechaInicioSTR.equals("")
                                                    || fechaInicioSTR.length() < 10)
                                                EntradaSalida.mostrarString(
                                                        "ERROR: Fecha no valida");
                                            else {
                                                LocalDate fechaInicio = LocalDate
                                                        .parse(fechaInicioSTR, DateTimeFormatter
                                                                .ofPattern("dd/MM/yyyy"));
                                                Asesoria asesoria = new Asesoria(empAsesoria,
                                                        areaAsesoria, fechaInicio);
                                                asesor.agregarAsesoria(asesoria);
                                                EntradaSalida
                                                        .mostrarString("Se ha incorporado una asesoria a un asesor");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (entidad == 4) {
                String usAdministrador = EntradaSalida.leerString("ALTA DE ADMINSTRADOR\nIngrese el usuario: ");
                if (usAdministrador.equals(""))
                    EntradaSalida.mostrarString("ERROR: usuario no valido");
                else {
                    String paAdministrador = EntradaSalida.leerString("Ingrese el password:");
                    if (paAdministrador.equals(""))
                        EntradaSalida.mostrarString("ERROR: password no valido");
                    else {
                        Administrador administrador = (Administrador) sistemaGestor
                                .buscarUsuario(usAdministrador + ":" + paAdministrador);
                        if (administrador != null)
                            EntradaSalida.mostrarString("ERROR: El usuario ya figura en el sistema");
                        else {
                            administrador = new Administrador(usAdministrador, paAdministrador);
                            sistemaGestor.agregarUsuario(administrador);
                            EntradaSalida.mostrarString("Se ha incorporado un administrador al sistema");
                        }
                    }
                }
            } else if (entidad == 5) {
                ArrayList<Pais> paises = sistemaGestor.getPaises();
                ArrayList<Area> areas = sistemaGestor.getAreas();
                boolean continuar = true;
                if (paises.size() == 0) {
                    EntradaSalida.mostrarString("NO HAY PAISES AGREGADOS, PRIMERO AGREGAR UNA PAIS");
                    continuar = false;
                }
                if (areas.size() == 0) {
                    EntradaSalida.mostrarString("NO HAY AREAS AGREGADAS, PRIMERO AGREGAR UN AREA");
                    continuar = false;
                }
                if (continuar) {
                    String nomEmpresa = EntradaSalida.leerString("ALTA DE EMPRESA\nIngrese el nombre: ");
                    if (nomEmpresa.equals(""))
                        EntradaSalida.mostrarString("ERROR: nombre no valido");
                    else {
                        String fechaEntradaSTR = EntradaSalida.leerString("Ingrese la fecha de entrada (DD/MM/AAAA):");
                        if (fechaEntradaSTR.equals("") || fechaEntradaSTR.length() < 10)
                            EntradaSalida.mostrarString("ERROR: Fecha no valida");
                        else {
                            LocalDate fechaEntrada = LocalDate.parse(fechaEntradaSTR,
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            double facturacionAnual = EntradaSalida.leerDouble("Ingrese la facturacion anual");
                            String paisSede = EntradaSalida.leerString("Ingrese el pais sede:");
                            if (paisSede.equals(""))
                                EntradaSalida.mostrarString("ERROR: Pais no valido");
                            else {
                                boolean encontrado = false;
                                int i = 0;
                                while (!encontrado && i < paises.size()) {
                                    if (paises.get(i).mismoNombre(paisSede))
                                        encontrado = true;
                                    else
                                        i++;
                                }
                                if (!encontrado)
                                    EntradaSalida.mostrarString("ERROR: El Pais no existe, agregarlo primero");
                                else {
                                    Pais paisEmpresa = paises.get(i);
                                    String ciudadSede = EntradaSalida.leerString("Ingrese la ciudad sede:");
                                    if (ciudadSede.equals(""))
                                        EntradaSalida.mostrarString("ERROR: Ciudad no valida");
                                    else {
                                        Empresa empresa = new Empresa(nomEmpresa, fechaEntrada, facturacionAnual,
                                                paisEmpresa, ciudadSede);
                                        sistemaGestor.agregarEmpresa(empresa);
                                        while (EntradaSalida.leerBoolean("¿Quisiera ingresar un Pais donde trabaja?")) {
                                            String nomPais = EntradaSalida.leerString("Ingrese el nombre del pais:");
                                            if (nomPais.equals(""))
                                                EntradaSalida.mostrarString("ERROR: Nombre no valido");
                                            else if (nomPais.equals(empresa.getPaisSede().getNombre()))
                                                EntradaSalida
                                                        .mostrarString("ERROR: Es el pais sede, no se puede ingresar");
                                            else {
                                                encontrado = false;
                                                i = 0;
                                                while (!encontrado && i < paises.size()) {
                                                    if (paises.get(i).mismoNombre(nomPais))
                                                        encontrado = true;
                                                    else
                                                        i++;
                                                }
                                                if (!encontrado)
                                                    EntradaSalida
                                                            .mostrarString(
                                                                    "ERROR: El Pais no existe, agregarlo primero");
                                                else {
                                                    Pais paisTrabajo = paises.get(i);
                                                    empresa.agregarPais(paisTrabajo);
                                                }
                                            }
                                        }
                                        while (EntradaSalida
                                                .leerBoolean("¿Quisiera ingresar una Area donde trabaja?")) {
                                            String nomArea = EntradaSalida.leerString("Ingrese el area:");
                                            if (nomArea.equals(""))
                                                EntradaSalida.mostrarString("ERROR: Nombre no valido");
                                            else {
                                                encontrado = false;
                                                i = 0;
                                                while (!encontrado && i < areas.size()) {
                                                    if (areas.get(i).mismoNombre(nomArea))
                                                        encontrado = true;
                                                    else
                                                        i++;
                                                }
                                                if (!encontrado)
                                                    EntradaSalida
                                                            .mostrarString(
                                                                    "ERROR: El Area no existe, agregarla primero");
                                                else {
                                                    Area areaTrabajo = areas.get(i);
                                                    empresa.agregarArea(areaTrabajo);
                                                }
                                            }
                                        }
                                        EntradaSalida.mostrarString("Se ha incorporado una empresa al sistema");
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (entidad == 6) {
                String nomArea = EntradaSalida.leerString("ALTA DE AREA\nIngrese el nombre: ");
                if (nomArea.equals(""))
                    EntradaSalida.mostrarString("ERROR: Nombre no valido");
                else {
                    String descArea = EntradaSalida.leerString("Ingrese la descripcion:");
                    if (descArea.equals(""))
                        EntradaSalida.mostrarString("ERROR: Descripcion no valida");
                    else {
                        Area area = new Area(nomArea, descArea);
                        sistemaGestor.agregarArea(area);
                        EntradaSalida.mostrarString("Se ha incorporado el area al sistema");
                    }
                }
            } else if (entidad == 7) {
                ArrayList<Empresa> empresas = sistemaGestor.getEmpresas();
                if (empresas.size() == 0)
                    EntradaSalida.mostrarString("NO HAY EMPRESAS AGREGADAS, PRIMERO AGREGAR UNA EMPRESA");
                else {
                    String nomArea = EntradaSalida.leerString("AGREGAR AREA\nIngrese el nombre del area a agregar: ");
                    if (nomArea.equals(""))
                        EntradaSalida.mostrarString("ERROR: Nombre no valido");
                    else {
                        ArrayList<Area> areas = sistemaGestor.getAreas();
                        boolean encontrado = false;
                        int i = 0;
                        while (!encontrado && i < areas.size()) {
                            if (areas.get(i).mismoNombre(nomArea))
                                encontrado = true;
                            else
                                i++;
                        }
                        if (!encontrado)
                            EntradaSalida
                                    .mostrarString("ERROR: El Area no existe, agregarla primero");
                        else {
                            Area area = areas.get(i);
                            String nomEmpresa = EntradaSalida.leerString("Ingrese el nombre de la empresa: ");
                            if (nomEmpresa.equals(""))
                                EntradaSalida.mostrarString("ERROR: Nombre no valido");
                            else {
                                encontrado = false;
                                i = 0;
                                while (!encontrado && i < empresas.size()) {
                                    if (empresas.get(i).mismoNombre(nomEmpresa))
                                        encontrado = true;
                                    else
                                        i++;
                                }
                                if (!encontrado)
                                    EntradaSalida
                                            .mostrarString("ERROR: La empresa no existe, agregarla primero");
                                else {
                                    Empresa empresa = empresas.get(i);
                                    empresa.agregarArea(area);
                                    EntradaSalida.mostrarString("Se ha agregado un area a una empresa");
                                }
                            }
                        }
                    }
                }
            } else if (entidad == 8) {
                String nomPais = EntradaSalida.leerString("ALTA DE PAIS\nIngrese el nombre: ");
                if (nomPais.equals(""))
                    EntradaSalida.mostrarString("ERROR: Nombre no valido");
                else {
                    Double PBI = EntradaSalida.leerDouble("Ingrese el PBI:");
                    Double numeroHabitantes = EntradaSalida.leerDouble("Ingrese el numero de habitantes:");
                    String capital = EntradaSalida.leerString("Ingrese la capital: ");
                    if (capital.equals(""))
                        EntradaSalida.mostrarString("ERROR: Nombre no valido");
                    else {
                        Pais pais = new Pais(nomPais, PBI, numeroHabitantes, capital);
                        sistemaGestor.agregarPais(pais);
                        EntradaSalida.mostrarString("Se ha incorporado el pais al sistema");
                    }

                }
            } else if (entidad == 9) {
                ArrayList<Empresa> empresas = sistemaGestor.getEmpresas();
                if (empresas.size() == 0)
                    EntradaSalida.mostrarString("NO HAY EMPRESAS AGREGADAS, PRIMERO AGREGAR UNA EMPRESA");
                else {
                    String nomPais = EntradaSalida.leerString("AGREGAR PAIS\nIngrese el nombre del pais a agregar: ");
                    if (nomPais.equals(""))
                        EntradaSalida.mostrarString("ERROR: Nombre no valido");
                    else {
                        ArrayList<Pais> paises = sistemaGestor.getPaises();
                        boolean encontrado = false;
                        int i = 0;
                        while (!encontrado && i < paises.size()) {
                            if (paises.get(i).mismoNombre(nomPais))
                                encontrado = true;
                            else
                                i++;
                        }
                        if (!encontrado)
                            EntradaSalida
                                    .mostrarString("ERROR: El Pais no existe, agregarlo primero");
                        else {
                            Pais pais = paises.get(i);
                            String nomEmpresa = EntradaSalida.leerString("Ingrese el nombre de la empresa: ");
                            if (nomEmpresa.equals(""))
                                EntradaSalida.mostrarString("ERROR: Nombre no valido");
                            else {
                                encontrado = false;
                                i = 0;
                                while (!encontrado && i < empresas.size()) {
                                    if (empresas.get(i).mismoNombre(nomEmpresa))
                                        encontrado = true;
                                    else
                                        i++;
                                }
                                if (!encontrado)
                                    EntradaSalida
                                            .mostrarString("ERROR: La empresa no existe, agregarla primero");
                                else {
                                    Empresa empresa = empresas.get(i);
                                    empresa.agregarPais(pais);
                                    EntradaSalida.mostrarString("Se ha agregado un pais a una empresa");
                                }
                            }
                        }
                    }
                }
            }
        } else if (operacion == 2) {
            if (entidad == 1) {
                ArrayList<Vendedor> vendedores = new ArrayList<Vendedor>();
                Visitor v = new obtenerV();

                for (Usuario usuario : usuarios) {
                    if (v.visit(usuario) != null)
                        vendedores.add((Vendedor) usuario);
                }

                String codVendedor = EntradaSalida.leerString("ELIMINAR VENDEDOR\nIngrese el codigo: ");
                if (codVendedor.equals(""))
                    EntradaSalida.mostrarString("ERROR: codigo no valido");
                else {
                    boolean encontrado = false;
                    int i = 0;
                    while (!encontrado && i < vendedores.size()) {
                        if (vendedores.get(i).mismoCodigo(codVendedor))
                            encontrado = true;
                        else
                            i++;
                    }
                    if (!encontrado)
                        EntradaSalida.mostrarString("ERROR: El codigo no se encuentra");
                    else {
                        Vendedor vendedorAEliminar = vendedores.get(i);
                        sistemaGestor.eliminarUsuario(vendedorAEliminar);
                        vendedorAEliminar.getEmpresa().restarVendedor();
                        for (int j = 0; j < vendedores.size(); j++)
                            vendedores.get(j).eliminarVendedorInferior(vendedorAEliminar);
                        for (int j = 0; j < vendedores.size(); j++)
                            vendedores.get(j).eliminarVendedorSuperior(vendedorAEliminar);
                        EntradaSalida.mostrarString("Se ha eliminado un vendedor");
                    }

                }
            } else if (entidad == 2) {
                ArrayList<Asesor> asesores = new ArrayList<Asesor>();
                Visitor v = new obtenerAs();

                for (Usuario usuario : usuarios) {
                    if (v.visit(usuario) != null)
                        asesores.add((Asesor) usuario);
                }

                String codAsesor = EntradaSalida.leerString("ELIMINAR ASESOR\nIngrese el codigo: ");
                if (codAsesor.equals(""))
                    EntradaSalida.mostrarString("ERROR: codigo no valido");
                else {
                    boolean encontrado = false;
                    int i = 0;
                    while (!encontrado && i < asesores.size()) {
                        if (asesores.get(i).mismoCodigo(codAsesor))
                            encontrado = true;
                        else
                            i++;
                    }
                    if (!encontrado)
                        EntradaSalida.mostrarString("ERROR: El codigo no se encuentra");
                    else {
                        sistemaGestor.eliminarUsuario(asesores.get(i));
                        EntradaSalida.mostrarString("Se ha eliminado un asesor");
                    }
                }
            } else if (entidad == 3) {
                ArrayList<Asesor> asesores = new ArrayList<Asesor>();
                Visitor v = new obtenerAs();

                for (Usuario usuario : usuarios) {
                    if (v.visit(usuario) != null)
                        asesores.add((Asesor) usuario);
                }

                String codAsesor = EntradaSalida.leerString("ELIMINAR ASESORIA\nIngrese el codigo de asesor: ");
                if (codAsesor.equals(""))
                    EntradaSalida.mostrarString("ERROR: codigo no valido");
                else {
                    boolean encontrado = false;
                    int i = 0;
                    while (!encontrado && i < asesores.size()) {
                        if (asesores.get(i).mismoCodigo(codAsesor))
                            encontrado = true;
                        else
                            i++;
                    }
                    if (!encontrado)
                        EntradaSalida.mostrarString("ERROR: El codigo no se encuentra");
                    else {
                        Asesor asesor = asesores.get(i);
                        ArrayList<Asesoria> asesorias = asesor.getAsesorias();
                        if (asesorias.size() == 0)
                            EntradaSalida.mostrarString("El asesor no tiene asesorias");
                        else {
                            for (Asesoria asesoria : asesorias) {
                                EntradaSalida.mostrarString(asesorias.indexOf(asesoria) + 1 + "-");
                                asesoria.mostrar();
                            }
                            int opcion = EntradaSalida.leerInt("Ingrese el numero de asesoria que quiere eliminar: ");
                            if (opcion >= 1 && opcion <= asesorias.size()) {
                                asesor.eliminarAsesoria(opcion);
                                EntradaSalida.mostrarString("Se ha eliminado una asesoria de un asesor");
                            } else
                                EntradaSalida.mostrarString("ERROR: Numero de asesoria no valido");
                        }
                    }

                }
            } else if (entidad == 4) {
                ArrayList<Administrador> administradores = new ArrayList<Administrador>();
                Visitor v = new obtenerAs();

                for (Usuario usuario : usuarios) {
                    if (v.visit(usuario) != null)
                        administradores.add((Administrador) usuario);
                }

                String usAdministrador = EntradaSalida.leerString("ELIMINAR ADMINISTRADOR\nIngrese el usuario: ");
                if (usAdministrador.equals(""))
                    EntradaSalida.mostrarString("ERROR: Usuario no valido");
                else {
                    boolean encontrado = false;
                    int i = 0;
                    while (!encontrado && i < administradores.size()) {
                        if (administradores.get(i).mismoUsuario(usAdministrador))
                            encontrado = true;
                        else
                            i++;
                    }
                    if (!encontrado)
                        EntradaSalida.mostrarString("ERROR: El nombre de usuario no se encuentra");
                    else {
                        sistemaGestor.eliminarUsuario(administradores.get(i));
                        EntradaSalida.mostrarString("Se ha eliminado una administrador");
                    }
                }
            } else if (entidad == 5) {
                ArrayList<Empresa> empresas = sistemaGestor.getEmpresas();

                String nomEmpresa = EntradaSalida.leerString("ELIMINAR EMPRESA\nIngrese el nombre: ");
                if (nomEmpresa.equals(""))
                    EntradaSalida.mostrarString("ERROR: Empresa no valida");
                else {
                    boolean encontrado = false;
                    int i = 0;
                    while (!encontrado && i < empresas.size()) {
                        if (empresas.get(i).mismoNombre(nomEmpresa))
                            encontrado = true;
                        else
                            i++;
                    }
                    if (!encontrado)
                        EntradaSalida.mostrarString("ERROR: El nombre de empresa no se encuentra");
                    else {
                        Empresa empresaAEliminar = empresas.get(i);
                        sistemaGestor.eliminarEmpresa(empresaAEliminar);

                        ArrayList<Vendedor> vendedores = new ArrayList<Vendedor>();
                        Visitor v = new obtenerV();

                        for (Usuario usuario : usuarios) {
                            if (v.visit(usuario) != null)
                                vendedores.add((Vendedor) usuario);
                        }
                        for (int j = 0; j < vendedores.size(); j++) {
                            if (vendedores.get(j).getEmpresa().equals(empresaAEliminar))
                                sistemaGestor.eliminarUsuario(vendedores.get(j));
                        }

                        ArrayList<Asesor> asesores = new ArrayList<Asesor>();
                        v = new obtenerAs();

                        for (Usuario usuario : usuarios) {
                            if (v.visit(usuario) != null)
                                asesores.add((Asesor) usuario);
                        }
                        ArrayList<Asesoria> asesorias = new ArrayList<Asesoria>();
                        for (int j = 0; j < asesores.size(); j++) {
                            Asesor asesor = asesores.get(j);
                            asesorias = asesor.getAsesorias();
                            for (int k = 0; k < asesorias.size(); k++) {
                                Asesoria asesoria = asesorias.get(k);
                                if (asesoria.getEmpresa().equals(empresaAEliminar))
                                    asesorias.remove(asesoria);
                            }
                        }
                        EntradaSalida.mostrarString("Se ha eliminado una empresa con todos sus vendedores y asesorias");
                    }
                }
            } else if (entidad == 6) {
                ArrayList<Area> areas = sistemaGestor.getAreas();

                String nomArea = EntradaSalida.leerString("ELIMINAR AREA\nIngrese el nombre: ");
                if (nomArea.equals(""))
                    EntradaSalida.mostrarString("ERROR: Area no valida");
                else {
                    boolean encontrado = false;
                    int i = 0;
                    while (!encontrado && i < areas.size()) {
                        if (areas.get(i).mismoNombre(nomArea))
                            encontrado = true;
                        else
                            i++;
                    }
                    if (!encontrado)
                        EntradaSalida.mostrarString("ERROR: El nombre de area no se encuentra");
                    else {
                        ArrayList<Empresa> empresas = sistemaGestor.getEmpresas();
                        encontrado = false;
                        i = 0;
                        while (!encontrado && i < empresas.size()) {
                            Empresa empresa = empresas.get(i);
                            areas = empresa.getAreas();
                            int j = 0;
                            while (!encontrado && j < areas.size()) {
                                if (areas.get(j).mismoNombre(nomArea))
                                    encontrado = true;
                                else
                                    j++;
                            }
                            if (!encontrado)
                                i++;
                        }
                        if (encontrado)
                            EntradaSalida.mostrarString(
                                    "ERROR: Area utilizada. Primero elimine las empresas que usan esa area");
                        else {
                            ArrayList<Asesor> asesores = new ArrayList<Asesor>();
                            Visitor v = new obtenerAs();

                            for (Usuario usuario : usuarios) {
                                if (v.visit(usuario) != null)
                                    asesores.add((Asesor) usuario);
                            }

                            encontrado = false;
                            i = 0;
                            while (!encontrado && i < asesores.size()) {
                                Asesor asesor = asesores.get(i);
                                ArrayList<Asesoria> asesorias = asesor.getAsesorias();
                                int j = 0;
                                while (!encontrado && j < asesorias.size()) {
                                    if (asesorias.get(j).getArea().getNombre().equals(nomArea))
                                        encontrado = true;
                                    else
                                        j++;
                                }
                                if (!encontrado)
                                    i++;
                            }
                            if (encontrado)
                                EntradaSalida.mostrarString(
                                        "ERROR: Area utilizada. Primero elimine las asesorias que usan esa area");
                            else {
                                sistemaGestor.eliminarArea(areas.get(i));
                                EntradaSalida.mostrarString("Se ha eliminado una area");
                            }
                        }
                    }

                }
            } else if (entidad == 7) {
                ArrayList<Area> areas = sistemaGestor.getAreas();
                ArrayList<Empresa> empresas = sistemaGestor.getEmpresas();
                Empresa empresaUtilizada = null;
                Area areaUtilizada = null;

                String nomArea = EntradaSalida.leerString("ELIMINAR AREA A EMPRESA\nIngrese el nombre de area: ");
                if (nomArea.equals(""))
                    EntradaSalida.mostrarString("ERROR: Area no valida");
                else {
                    boolean encontrado = false;
                    int i = 0;
                    while (!encontrado && i < areas.size()) {
                        if (areas.get(i).mismoNombre(nomArea)) {
                            areaUtilizada = areas.get(i);
                            encontrado = true;
                        } else
                            i++;
                    }
                    if (!encontrado)
                        EntradaSalida.mostrarString("ERROR: El nombre de area no se encuentra");
                    else {
                        String nomEmpresa = EntradaSalida.leerString("Ingrese el nombre de empresa: ");
                        if (nomEmpresa.equals(""))
                            EntradaSalida.mostrarString("ERROR: Empresa no valida");
                        else {
                            encontrado = false;
                            i = 0;
                            while (!encontrado && i < empresas.size()) {
                                if (empresas.get(i).mismoNombre(nomEmpresa)) {
                                    empresaUtilizada = empresas.get(i);
                                    encontrado = true;
                                } else
                                    i++;
                            }
                            if (!encontrado)
                                EntradaSalida.mostrarString("ERROR: El nombre de empresa no se encuentra");
                            else {
                                ArrayList<Asesor> asesores = new ArrayList<Asesor>();
                                Visitor v = new obtenerAs();

                                for (Usuario usuario : usuarios) {
                                    if (v.visit(usuario) != null)
                                        asesores.add((Asesor) usuario);
                                }

                                encontrado = false;
                                i = 0;
                                while (!encontrado && i < asesores.size()) {
                                    Asesor asesor = asesores.get(i);
                                    ArrayList<Asesoria> asesorias = asesor.getAsesorias();
                                    int j = 0;
                                    while (!encontrado && j < asesorias.size()) {
                                        if (asesorias.get(j).getArea().getNombre().equals(nomArea)
                                                && asesorias.get(j).getEmpresa().getNombre().equals(nomEmpresa))
                                            encontrado = true;
                                        else
                                            j++;
                                    }
                                    if (!encontrado)
                                        i++;
                                }
                                if (encontrado)
                                    EntradaSalida.mostrarString(
                                            "ERROR: Existe una asesoria en esa empresa y esa area, eliminela primero");
                                else {
                                    empresaUtilizada.eliminarArea(areaUtilizada);
                                    EntradaSalida.mostrarString("Se ha eliminado una area en una empresa");
                                }
                            }
                        }
                    }
                }
            } else if (entidad == 8) {
                ArrayList<Pais> paises = sistemaGestor.getPaises();

                String nomPais = EntradaSalida.leerString("ELIMINAR PAIS\nIngrese el nombre: ");
                if (nomPais.equals(""))
                    EntradaSalida.mostrarString("ERROR: Pais no valido");
                else {
                    boolean encontrado = false;
                    int i = 0;
                    while (!encontrado && i < paises.size()) {
                        if (paises.get(i).mismoNombre(nomPais))
                            encontrado = true;
                        else
                            i++;
                    }
                    if (!encontrado)
                        EntradaSalida.mostrarString("ERROR: El nombre de pais no se encuentra");
                    else {
                        ArrayList<Empresa> empresas = sistemaGestor.getEmpresas();
                        encontrado = false;
                        i = 0;
                        while (!encontrado && i < empresas.size()) {
                            Empresa empresa = empresas.get(i);
                            paises = empresa.getPaises();
                            int j = 0;
                            if (empresa.getPaisSede().getNombre().equals(nomPais))
                                encontrado = true;
                            while (!encontrado && j < paises.size()) {
                                if (paises.get(j).mismoNombre(nomPais))
                                    encontrado = true;
                                else
                                    j++;
                            }
                            if (!encontrado)
                                i++;
                        }
                        if (encontrado)
                            EntradaSalida.mostrarString(
                                    "ERROR: Pais utilizado. Primero elimine las empresas que usan ese pais");
                        else {
                            sistemaGestor.eliminarPais(paises.get(i));
                            EntradaSalida.mostrarString("Se ha eliminado una pais");
                        }
                    }
                }
            } else if (entidad == 9) {
                ArrayList<Pais> paises = sistemaGestor.getPaises();
                ArrayList<Empresa> empresas = sistemaGestor.getEmpresas();
                Empresa empresaUtilizada = null;
                Pais paisUtilizado = null;

                String nomPais = EntradaSalida.leerString("ELIMINAR PAIS A EMPRESA\nIngrese el nombre del pais: ");
                if (nomPais.equals(""))
                    EntradaSalida.mostrarString("ERROR: Pais no valido");
                else {
                    boolean encontrado = false;
                    int i = 0;
                    while (!encontrado && i < paises.size()) {
                        if (paises.get(i).mismoNombre(nomPais)) {
                            paisUtilizado = paises.get(i);
                            encontrado = true;
                        } else
                            i++;
                    }
                    if (!encontrado)
                        EntradaSalida.mostrarString("ERROR: El nombre de pais no se encuentra");
                    else {
                        String nomEmpresa = EntradaSalida.leerString("Ingrese el nombre de empresa: ");
                        if (nomEmpresa.equals(""))
                            EntradaSalida.mostrarString("ERROR: Empresa no valida");
                        else {
                            encontrado = false;
                            i = 0;
                            while (!encontrado && i < empresas.size()) {
                                if (empresas.get(i).mismoNombre(nomEmpresa)) {
                                    empresaUtilizada = empresas.get(i);
                                    encontrado = true;
                                } else
                                    i++;
                            }
                            if (!encontrado)
                                EntradaSalida.mostrarString("ERROR: El nombre de empresa no se encuentra");
                            else {
                                empresaUtilizada.eliminarPais(paisUtilizado);
                                EntradaSalida.mostrarString("Se ha eliminado un pais en una empresa");
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean mismoUsuario(String usAdministrador) {
        return usAdministrador.equals(usuario);
    }

    @Override
    public Usuario accept(Visitor v) {
        return v.visit(this);
    }

    public Administrador devolvete() {
        return this;
    }

    @Override
    public void mostrar() {
        EntradaSalida.mostrarString("");
        EntradaSalida.mostrarString("ADMINISTRADOR " + usuario);
        EntradaSalida.mostrarString("");
    }
}
