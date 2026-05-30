package co.edu.javeriana.guarderiaadswagugu.controllers;

import co.edu.javeriana.guarderiaadswagugu.modelo.empleados.Empleado;
import co.edu.javeriana.guarderiaadswagugu.modelo.empleados.Evaluacion;
import co.edu.javeriana.guarderiaadswagugu.modelo.guarderia.*;
import co.edu.javeriana.guarderiaadswagugu.modelo.ninos.*;
import co.edu.javeriana.guarderiaadswagugu.modelo.usuarios.Usuario;
import co.edu.javeriana.guarderiaadswagugu.dao.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AguguTarjeteroController implements Initializable {

    // ─── TABS ────────────────────────────────────────────────────────────────
    @FXML private TabPane tabPanePrincipal;
    @FXML private Tab tabLogin, tabInscripcion, tabDieta, tabActividades;
    @FXML private Tab tabEvaluacion, tabConsolidado, tabVacunas, tabSerializacion;

    // ─── LOGIN ───────────────────────────────────────────────────────────────
    @FXML private TextField txtUsuarioLogin;
    @FXML private PasswordField txtContrasenaLogin;
    @FXML private Label lblRolActual, lblRolDescripcion;
    @FXML private Button btnIngresarSistema, btnLimpiarLogin, btnCerrarSesion;

    // ─── INSCRIPCIÓN ─────────────────────────────────────────────────────────
    @FXML private TextField txtNombreNino, txtRegistroCivil, txtMarcaPanal;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private ComboBox<String> cmbCategoriaNino, cmbSucursalNino, cmbLocalidad;
    @FXML private TextField txtNombreAcudiente, txtDocumentoAcudiente;
    @FXML private TextField txtDireccionAcudiente, txtTelefonoAcudiente, txtSalarioAcudiente;
    @FXML private Button btnInscribirNino, btnLimpiarInscripcion;
    @FXML private Label lblResultadoCupo;
    @FXML private TableView<NinoFila> tblNinos;
    @FXML private TableColumn<NinoFila, String> colNombreNino, colCategoriaNino, colSucursalNino;
    @FXML private Button btnExportarInscritosCsv;
    @FXML private TextField txtBuscarNino;

    // ─── DIETA ───────────────────────────────────────────────────────────────
    @FXML private TextField txtIdNinoDieta, txtNombreNinoDieta;
    @FXML private ComboBox<String> cmbEspecialistaDieta;
    @FXML private TextField txtAlimentoPermitido, txtCantidadPermitida;
    @FXML private TextField txtAlimentoProhibido;
    @FXML private TextArea  txtRazonProhibicion;
    @FXML private Button btnGuardarDieta, btnConsultarDieta, btnActualizarDieta;
    @FXML private Button btnExportarDietaTxt;
    @FXML private TableView<AlimentoFila> tblDieta;
    @FXML private TableColumn<AlimentoFila, String> colAlimento, colCantidad, colTipoAlimento, colRazon;

    // ─── ACTIVIDADES ─────────────────────────────────────────────────────────
    @FXML private TextField txtNombreActividad, txtCostoBase, txtLugarActividad;
    @FXML private DatePicker dpFechaActividad;
    @FXML private ComboBox<String> cmbTipoActividad, cmbSucursalActividad;
    @FXML private ComboBox<String> cmbEmpleadoResponsableActividad;
    @FXML private CheckBox chkPadreAcompana;
    @FXML private Button btnCrearActividad, btnCalcularCosto, btnAsociarNinoActividad;
    @FXML private TextField txtCostoFinal, txtDescuento;
    @FXML private Button btnExportarActividadJson;
    @FXML private TableView<ActividadFila> tblActividades;
    @FXML private TableColumn<ActividadFila, String> colActividadNombre, colActividadTipo, colActividadSucursal;
    @FXML private TableColumn<ActividadFila, String> colActividadCosto;
    @FXML private TableView<NinoActividadFila> tblNinosActividad;
    @FXML private TableColumn<NinoActividadFila, String> colNinoActividad, colCategoriaActividad, colAcompanante, colCostoFinal;

    // ─── EVALUACIÓN ──────────────────────────────────────────────────────────
    @FXML private TextField txtIdEmpleado, txtNombreEmpleado, txtExperienciaEmpleado;
    @FXML private ComboBox<String> cmbCargoEmpleado;
    @FXML private TextField txtSalarioActual, txtPuntajeEvaluacion, txtNuevoSalario;
    @FXML private TextArea txtObservacionesEvaluacion;
    @FXML private Button btnBuscarEmpleado, btnRegistrarEvaluacion, btnCalcularAjuste;
    @FXML private Button btnGuardarEmpleadoJson;
    @FXML private TableView<EvaluacionFila> tblEvaluaciones;
    @FXML private TableColumn<EvaluacionFila, String> colEmpleado, colCargo, colPuntaje;
    @FXML private TableColumn<EvaluacionFila, String> colNuevoSalario, colObservaciones;

    // ─── CONSOLIDADO ─────────────────────────────────────────────────────────
    @FXML private ComboBox<String> cmbSucursalFinanciera;
    @FXML private TextField txtIngresosMatriculas, txtIngresosPensiones;
    @FXML private TextField txtIngresosJornadas, txtIngresosExtras;
    @FXML private TextField txtGastosSucursal, txtTotalIngresos, txtTotalGastos, txtBalance;
    @FXML private Button btnCalcularConsolidado, btnExportarConsolidadoCsv, btnGenerarResumenTxt;
    @FXML private TableView<ConsolidadoFila> tblConsolidado;
    @FXML private TableColumn<ConsolidadoFila, String> colSucursal, colIngresos, colGastos, colBalance;

    // ─── VACUNAS ─────────────────────────────────────────────────────────────
    @FXML private TextField txtIdNinoVacuna, txtNombreNinoVacuna, txtEnfermedad;
    @FXML private TextField txtLaboratorio, txtSerialVacuna, txtCostoVacuna;
    @FXML private DatePicker dpFechaDosis;
    @FXML private ComboBox<String> cmbTipoVacuna, cmbTipoDosis;
    @FXML private CheckBox chkPrimeraVez, chkRefuerzo, chkVacunaPrivada;
    @FXML private Button btnRegistrarDosis, btnActualizarCarne, btnConsultarPendientes;
    @FXML private Button btnExportarPendientesJson, btnGenerarGraficoTorta;
    @FXML private TableView<DosisFila> tblCarneVacunas;
    @FXML private TableColumn<DosisFila, String> colEnfermedad, colFechaDosis, colTipoDosis;
    @FXML private TableColumn<DosisFila, String> colLaboratorio, colSerial;
    @FXML private TableView<PendienteFila> tblVacunasPendientes;
    @FXML private TableColumn<PendienteFila, String> colNinoPendiente, colVacunaPendiente;
    @FXML private TableColumn<PendienteFila, String> colEdadPendiente, colSucursalPendiente;

    // ─── SERIALIZACIÓN ───────────────────────────────────────────────────────
    @FXML private TextField txtRutaArchivoDat, txtRutaBackupJson;
    @FXML private Button btnGuardarEstadoSistema, btnRestaurarEstadoSistema;
    @FXML private Button btnGuardarFichaJson, btnGenerarBackupJson, btnSeleccionarArchivo;
    @FXML private Button btnSerializarCarne;
    @FXML private TextArea txtLogSerializacion;
    @FXML private Label lblEstadoSerializacion;

    // ─── ESTADO DEL SISTEMA ──────────────────────────────────────────────────
    private Guarderia guarderia;
    private Usuario usuarioActual;
    private Nino ninoSeleccionado;
    private Empleado empleadoSeleccionado;
    private Actividad actividadSeleccionada;

    // DAOs
    private final NinoDAO ninoDAO = new NinoDAO();
    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private final ActividadDAO actividadDAO = new ActividadDAO();

    // ─── LISTAS OBSERVABLES ──────────────────────────────────────────────────
    private ObservableList<NinoFila>        listaInscritos    = FXCollections.observableArrayList();
    private ObservableList<AlimentoFila>    listaDieta        = FXCollections.observableArrayList();
    private ObservableList<ActividadFila>   listaActividades  = FXCollections.observableArrayList();
    private ObservableList<NinoActividadFila> listaNinosActiv = FXCollections.observableArrayList();
    private ObservableList<EvaluacionFila>  listaEvaluaciones = FXCollections.observableArrayList();
    private ObservableList<ConsolidadoFila> listaConsolidado  = FXCollections.observableArrayList();
    private ObservableList<DosisFila>       listaDosis        = FXCollections.observableArrayList();
    private ObservableList<PendienteFila>   listaPendientes   = FXCollections.observableArrayList();

    // ═════════════════════════════════════════════════════════════════════════
    // INITIALIZE
    // ═════════════════════════════════════════════════════════════════════════
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarGuarderia();
        configurarTablas();
        configurarCombos();
        bloquearTabs(true);

        // Listener para refrescar combos al cambiar de pestaña
        if (tabPanePrincipal != null) {
            tabPanePrincipal.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
                List<String> empleados = new ArrayList<>();
                for (Localidad l : guarderia.getLocalidades())
                    for (Sucursal s : l.getSucursales())
                        for (Empleado e : s.getEmpleados())
                            empleados.add(e.getNombre());

                if (newTab == tabDieta && cmbEspecialistaDieta != null)
                    cmbEspecialistaDieta.setItems(FXCollections.observableArrayList(empleados));

                if (newTab == tabActividades && cmbEmpleadoResponsableActividad != null)
                    cmbEmpleadoResponsableActividad.setItems(FXCollections.observableArrayList(empleados));

                if (newTab == tabEvaluacion && txtIdEmpleado != null) {
                    StringBuilder info = new StringBuilder();
                    for (Localidad l : guarderia.getLocalidades())
                        for (Sucursal s : l.getSucursales())
                            for (Empleado e : s.getEmpleados())
                                info.append(e.getNombre()).append(" - CC: ").append(e.getCedula()).append("\n");
                    txtIdEmpleado.setPromptText(info.toString().trim());
                }
            });
        }
    }

    // ─── Datos de prueba ─────────────────────────────────────────────────────
    private void inicializarGuarderia() {
        guarderia = new Guarderia("ADSw-AGUGU");

        Localidad loc1 = new Localidad("Chapinero");
        Localidad loc2 = new Localidad("Usaquén");
        guarderia.agregarLocalidad(loc1);
        guarderia.agregarLocalidad(loc2);

        Sucursal s1 = new Sucursal("Sede Norte", "Calle 100 #15-20", "Usaquén");
        Sucursal s2 = new Sucursal("Sede Chapinero", "Cra 7 #45-10", "Chapinero");
        loc1.agregarSucursal(s1);
        loc2.agregarSucursal(s2);

        // Empleados de prueba
        Empleado e1 = new Empleado("Ana García", 12345678,
                LocalDate.of(1985, 3, 10), "Calle 50 #20-30",
                "Psicóloga Infantil", 7890, "Psicóloga", 5, true);
        Empleado e2 = new Empleado("Carlos López", 87654321,
                LocalDate.of(1980, 7, 22), "Cra 30 #60-15",
                "Nutricionista Infantil", 4321, "Nutricionista", 8, false);
        s1.getEmpleados().add(e1);
        s1.getEmpleados().add(e2);

        // Niños de prueba
        Acostadito n1 = new Acostadito("Sofía Martínez",
                LocalDate.of(2025, 11, 1), 100001, "Pampers", "1", "NAN", 4, false);
        Aventurero n2 = new Aventurero("Juan Rodríguez",
                LocalDate.of(2025, 1, 15), 100002, "Huggies", "2",
                "NAN", 6, false, 101, "Osito", 9, true);
        s1.inscribirNino(n1);
        s1.inscribirNino(n2);

        // Solo guardar datos de prueba si no existe el archivo
        if (!new File("empleados.json").exists()) {
            List<Empleado> empleadosPrueba = new ArrayList<>();
            empleadosPrueba.add(e1);
            empleadosPrueba.add(e2);
            empleadoDAO.guardarTodos(empleadosPrueba);
        }
        if (!new File("ninos.json").exists()) {
            List<Nino> ninosPrueba = new ArrayList<>();
            ninosPrueba.add(n1);
            ninosPrueba.add(n2);
            ninoDAO.guardarTodos(ninosPrueba);
        }

    }

    // ─── Configurar columnas de tablas ───────────────────────────────────────
    private void configurarTablas() {
        // Tabla inscritos
        if (colNombreNino != null)    colNombreNino.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colCategoriaNino != null) colCategoriaNino.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        if (colSucursalNino != null)  colSucursalNino.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
        if (tblNinos != null) {
            TableColumn<NinoFila, String> colRc = new TableColumn<>("RC");
            colRc.setCellValueFactory(new PropertyValueFactory<>("rc"));
            colRc.setPrefWidth(90);
            tblNinos.getColumns().add(colRc);
            tblNinos.setItems(listaInscritos);
        }

        // Tabla dieta
        if (colAlimento != null)     colAlimento.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colCantidad != null)     colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        if (colTipoAlimento != null) colTipoAlimento.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        if (colRazon != null)        colRazon.setCellValueFactory(new PropertyValueFactory<>("razon"));
        if (tblDieta != null)        tblDieta.setItems(listaDieta);

        // Tabla actividades
        if (colActividadNombre != null)   colActividadNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colActividadTipo != null)     colActividadTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        if (colActividadSucursal != null) colActividadSucursal.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
        if (colActividadCosto != null)    colActividadCosto.setCellValueFactory(new PropertyValueFactory<>("costoBase"));
        if (tblActividades != null)       tblActividades.setItems(listaActividades);

        // Tabla niños asociados a actividad
        if (colNinoActividad != null)       colNinoActividad.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colCategoriaActividad != null)  colCategoriaActividad.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        if (colAcompanante != null)         colAcompanante.setCellValueFactory(new PropertyValueFactory<>("acompanante"));
        if (colCostoFinal != null)          colCostoFinal.setCellValueFactory(new PropertyValueFactory<>("costoFinal"));
        if (tblNinosActividad != null)      tblNinosActividad.setItems(listaNinosActiv);

        // Tabla evaluaciones
        if (colEmpleado != null)     colEmpleado.setCellValueFactory(new PropertyValueFactory<>("empleado"));
        if (colCargo != null)        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        if (colPuntaje != null)      colPuntaje.setCellValueFactory(new PropertyValueFactory<>("puntaje"));
        if (colNuevoSalario != null) colNuevoSalario.setCellValueFactory(new PropertyValueFactory<>("nuevoSalario"));
        if (colObservaciones != null)colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        if (tblEvaluaciones != null) tblEvaluaciones.setItems(listaEvaluaciones);

        // Tabla consolidado
        if (colSucursal != null)  colSucursal.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
        if (colIngresos != null)  colIngresos.setCellValueFactory(new PropertyValueFactory<>("ingresos"));
        if (colGastos != null)    colGastos.setCellValueFactory(new PropertyValueFactory<>("gastos"));
        if (colBalance != null)   colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        if (tblConsolidado != null) tblConsolidado.setItems(listaConsolidado);

        // Tabla vacunas
        if (colEnfermedad != null) colEnfermedad.setCellValueFactory(new PropertyValueFactory<>("enfermedad"));
        if (colFechaDosis != null) colFechaDosis.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        if (colTipoDosis != null)  colTipoDosis.setCellValueFactory(new PropertyValueFactory<>("tipoDosis"));
        if (colLaboratorio != null)colLaboratorio.setCellValueFactory(new PropertyValueFactory<>("laboratorio"));
        if (colSerial != null)     colSerial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        if (tblCarneVacunas != null) tblCarneVacunas.setItems(listaDosis);

        if (colNinoPendiente != null)    colNinoPendiente.setCellValueFactory(new PropertyValueFactory<>("nino"));
        if (tblVacunasPendientes != null) {
            TableColumn<PendienteFila, String> colRcPendiente = new TableColumn<>("RC");
            colRcPendiente.setCellValueFactory(new PropertyValueFactory<>("rc"));
            colRcPendiente.setPrefWidth(90);
            tblVacunasPendientes.getColumns().add(0, colRcPendiente);
        }
        if (colVacunaPendiente != null)  colVacunaPendiente.setCellValueFactory(new PropertyValueFactory<>("vacuna"));
        if (colEdadPendiente != null)    colEdadPendiente.setCellValueFactory(new PropertyValueFactory<>("edad"));
        if (colSucursalPendiente != null)colSucursalPendiente.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
        if (tblVacunasPendientes != null)tblVacunasPendientes.setItems(listaPendientes);
    }

    private void configurarCombos() {
        if (cmbCategoriaNino != null)
            cmbCategoriaNino.setItems(FXCollections.observableArrayList(
                    "Acostadito (0-6m)", "Aventurero (6-18m)", "Trotamundo (18-24m)",
                    "Jugueton (24-36m)", "Párvulo (36m-5a)"));

        List<String> sucursales = new ArrayList<>();
        List<String> localidades = new ArrayList<>();
        for (Localidad l : guarderia.getLocalidades()) {
            localidades.add(l.getNombre());
            for (Sucursal s : l.getSucursales()) sucursales.add(s.getNombre());
        }
        if (cmbSucursalNino != null)       cmbSucursalNino.setItems(FXCollections.observableArrayList(sucursales));
        if (cmbSucursalActividad != null)  cmbSucursalActividad.setItems(FXCollections.observableArrayList(sucursales));
        if (cmbSucursalFinanciera != null) cmbSucursalFinanciera.setItems(FXCollections.observableArrayList(sucursales));
        if (cmbLocalidad != null)          cmbLocalidad.setItems(FXCollections.observableArrayList(localidades));

        if (cmbTipoActividad != null)
            cmbTipoActividad.setItems(FXCollections.observableArrayList(
                    "Baile", "Títeres", "Marionetas", "Visita parque", "Piscina", "Museo"));

        if (cmbCargoEmpleado != null)
            cmbCargoEmpleado.setItems(FXCollections.observableArrayList(
                    "Psicóloga infantil", "Profesora preescolar",
                    "Nutricionista infantil", "Terapeuta de lenguaje", "Terapeuta ocupacional"));

        if (cmbTipoVacuna != null)
            cmbTipoVacuna.setItems(FXCollections.observableArrayList(
                    "BCG", "Hepatitis B", "Pentavalente", "Polio", "Rotavirus",
                    "Neumococo", "Influenza", "Varicela", "Triple viral"));

        if (cmbTipoDosis != null)
            cmbTipoDosis.setItems(FXCollections.observableArrayList(
                    "Primera vez", "Refuerzo 1", "Refuerzo 2", "Refuerzo 3"));
        // Llenar especialistas dieta
        if (cmbEspecialistaDieta != null) {
            List<String> empleados = new ArrayList<>();
            for (Localidad l : guarderia.getLocalidades())
                for (Sucursal s : l.getSucursales())
                    for (Empleado e : s.getEmpleados())
                        empleados.add(e.getNombre());
            cmbEspecialistaDieta.setItems(FXCollections.observableArrayList(empleados));
        }
        if (cmbEmpleadoResponsableActividad != null) {
            List<String> empleados = new ArrayList<>();
            for (Localidad l : guarderia.getLocalidades())
                for (Sucursal s : l.getSucursales())
                    for (Empleado e : s.getEmpleados())
                        empleados.add(e.getNombre());
            cmbEmpleadoResponsableActividad.setItems(FXCollections.observableArrayList(empleados));
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // LOGIN
    // ═════════════════════════════════════════════════════════════════════════
    @FXML
    private void onIngresarSistema() {
        String user = txtUsuarioLogin.getText().trim();
        String pass = txtContrasenaLogin.getText().trim();

        // Usuarios de prueba hardcodeados
        if (user.equals("admin") && pass.equals("1234")) {
            usuarioActual = new Usuario("admin", "1234", Usuario.Rol.ADMIN_SUCURSAL, "S1");
        } else if (user.equals("especialista") && pass.equals("1234")) {
            usuarioActual = new Usuario("especialista", "1234", Usuario.Rol.ESPECIALISTA, "S1");
        } else if (user.equals("adminloc") && pass.equals("1234")) {
            usuarioActual = new Usuario("adminloc", "1234", Usuario.Rol.ADMIN_LOCALIDAD, "L1");
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Login fallido",
                    "Usuario o contraseña incorrectos.\n\nUsuarios de prueba:\n" +
                            "  admin / 1234  (Admin sucursal)\n" +
                            "  especialista / 1234\n" +
                            "  adminloc / 1234  (Admin localidad)");
            return;
        }
        lblRolActual.setText(usuarioActual.getRol().name().replace("_", " "));
        lblRolDescripcion.setText("Sesion activa: " + user);
        aplicarPermisosPorRol(usuarioActual.getRol());
        recargarTodosLosInscritos();
    }

    @FXML
    private void onLimpiarLogin() {
        txtUsuarioLogin.clear();
        txtContrasenaLogin.clear();
    }

    @FXML
    private void onCerrarSesion() {
        usuarioActual = null;
        lblRolActual.setText("Sin iniciar sesión");
        lblRolDescripcion.setText("Ingrese desde la pestaña Login para habilitar módulos.");
        bloquearTabs(true);
        tabPanePrincipal.getSelectionModel().select(tabLogin);
    }

    private void bloquearTabs(boolean bloquear) {
        if (tabInscripcion != null)   tabInscripcion.setDisable(bloquear);
        if (tabDieta != null)         tabDieta.setDisable(bloquear);
        if (tabActividades != null)   tabActividades.setDisable(bloquear);
        if (tabEvaluacion != null)    tabEvaluacion.setDisable(bloquear);
        if (tabConsolidado != null)   tabConsolidado.setDisable(bloquear);
        if (tabVacunas != null)       tabVacunas.setDisable(bloquear);
        if (tabSerializacion != null) tabSerializacion.setDisable(bloquear);
    }

    private void aplicarPermisosPorRol(Usuario.Rol rol) {
        // Primero bloquear todo
        bloquearTabs(true);

        switch (rol) {
            case ADMIN_SUCURSAL -> {
                // Acceso completo a todo
                bloquearTabs(false);
                tabPanePrincipal.getSelectionModel().select(tabInscripcion);
                lblRolDescripcion.setText("Acceso completo: inscripcion, dieta, actividades, evaluacion, consolidado, vacunas, serializacion.");
            }
            case ESPECIALISTA -> {
                // Solo Dieta y Vacunas
                if (tabDieta != null)   tabDieta.setDisable(false);
                if (tabVacunas != null) tabVacunas.setDisable(false);
                tabPanePrincipal.getSelectionModel().select(tabDieta);
                lblRolDescripcion.setText("Acceso: Dieta personalizada y Carne de vacunas.");
            }
            case ADMIN_LOCALIDAD -> {
                // Inscripcion, Consolidado y Serializacion
                if (tabInscripcion != null)   tabInscripcion.setDisable(false);
                if (tabConsolidado != null)   tabConsolidado.setDisable(false);
                if (tabSerializacion != null) tabSerializacion.setDisable(false);
                tabPanePrincipal.getSelectionModel().select(tabInscripcion);
                lblRolDescripcion.setText("Acceso: Inscripcion, Consolidado financiero y Serializacion.");
            }
            default -> bloquearTabs(true);
        }
    }

    // Navegación desde menú lateral
    @FXML private void onAbrirInicio()       { tabPanePrincipal.getSelectionModel().select(tabLogin); }
    @FXML private void onAbrirInscripcion()  { if (tabInscripcion != null && !tabInscripcion.isDisable()) tabPanePrincipal.getSelectionModel().select(tabInscripcion); }
    @FXML private void onAbrirDieta() {
        if (tabDieta != null && !tabDieta.isDisable()) {
            // Refrescar especialistas
            if (cmbEspecialistaDieta != null) {
                List<String> empleados = new ArrayList<>();
                for (Localidad l : guarderia.getLocalidades())
                    for (Sucursal s : l.getSucursales())
                        for (Empleado e : s.getEmpleados())
                            empleados.add(e.getNombre());
                cmbEspecialistaDieta.setItems(FXCollections.observableArrayList(empleados));
            }
            tabPanePrincipal.getSelectionModel().select(tabDieta);
        }
    }
    @FXML private void onAbrirActividades() {
        if (tabActividades != null && !tabActividades.isDisable()) {
            // Refrescar empleados responsables
            if (cmbEmpleadoResponsableActividad != null) {
                List<String> empleados = new ArrayList<>();
                for (Localidad l : guarderia.getLocalidades())
                    for (Sucursal s : l.getSucursales())
                        for (Empleado e : s.getEmpleados())
                            empleados.add(e.getNombre());
                cmbEmpleadoResponsableActividad.setItems(FXCollections.observableArrayList(empleados));
            }
            tabPanePrincipal.getSelectionModel().select(tabActividades);
        }
    }
    @FXML private void onAbrirEvaluacion() {
        if (tabEvaluacion != null && !tabEvaluacion.isDisable()) {
            if (txtIdEmpleado != null) {
                StringBuilder info = new StringBuilder();
                for (Localidad l : guarderia.getLocalidades())
                    for (Sucursal s : l.getSucursales())
                        for (Empleado e : s.getEmpleados())
                            info.append(e.getNombre()).append(" - CC: ").append(e.getCedula()).append("\n");
                txtIdEmpleado.setPromptText(info.toString().trim());
            }
            tabPanePrincipal.getSelectionModel().select(tabEvaluacion);
        }
    }

    @FXML private void onAbrirConsolidado()  { if (tabConsolidado != null && !tabConsolidado.isDisable()) tabPanePrincipal.getSelectionModel().select(tabConsolidado); }
    @FXML private void onAbrirVacunas()      { if (tabVacunas != null && !tabVacunas.isDisable()) tabPanePrincipal.getSelectionModel().select(tabVacunas); }
    @FXML private void onAbrirSerializacion(){ if (tabSerializacion != null && !tabSerializacion.isDisable()) tabPanePrincipal.getSelectionModel().select(tabSerializacion); }

    // ═════════════════════════════════════════════════════════════════════════
    // SERVICIO 1 – INSCRIPCIÓN DE NIÑO
    // ═════════════════════════════════════════════════════════════════════════
    @FXML
    private void onInscribirNino() {
        if (usuarioActual == null || !usuarioActual.puedeInscribirNino()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin permiso",
                    "Solo el administrador de sucursal puede inscribir niños.");
            return;
        }
        String nombre = txtNombreNino.getText().trim();
        String rcStr  = txtRegistroCivil.getText().trim();
        String marca  = txtMarcaPanal.getText().trim();
        String cat    = cmbCategoriaNino.getValue();
        String sucNom = cmbSucursalNino.getValue();

        if (nombre.isEmpty() || rcStr.isEmpty() || cat == null || sucNom == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
                    "Complete nombre, registro civil, categoría y sucursal.");
            return;
        }

        LocalDate fn = (dpFechaNacimiento.getValue() != null)
                ? dpFechaNacimiento.getValue() : LocalDate.now().minusMonths(3);
        int rc;
        try { rc = Integer.parseInt(rcStr); }
        catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Registro civil debe ser numérico."); return;
        }

        Sucursal suc = buscarSucursal(sucNom);
        if (suc == null) { mostrarAlerta(Alert.AlertType.ERROR, "Error", "Sucursal no encontrada."); return; }

        // Crear acudiente si hay datos
        Acudiente acudiente = null;
        if (!txtNombreAcudiente.getText().trim().isEmpty()) {
            double sal = 0;
            try { sal = Double.parseDouble(txtSalarioAcudiente.getText().trim()); } catch (Exception ignored) {}
            acudiente = new Acudiente(
                    txtNombreAcudiente.getText().trim(),
                    txtDireccionAcudiente.getText().trim(),
                    "", txtTelefonoAcudiente.getText().trim(), sal);
        }

        Nino nino = crearNinoPorCategoria(cat, nombre, fn, rc, marca.isEmpty() ? "Genérica" : marca);
        if (nino == null) return;

        boolean inscrito = suc.inscribirNino(nino);
        String msg;
        if (inscrito) {
            msg = "✓ Niño inscrito exitosamente en " + sucNom;
            if (acudiente != null) {
                acudiente.agregarNino(nino);
                double pension = acudiente.calcularPension(acudiente.getSalario());
                msg += "\nPensión mensual: $" + String.format("%,.0f", pension);
            }
            lblResultadoCupo.setText("Inscrito en " + sucNom);
            ninoSeleccionado = nino;
            try {
                ninoDAO.agregar(nino);
                log("Nino guardado en JSON: " + nino.getNombre());
            } catch (Exception e) {
                log("Error al guardar nino en JSON: " + e.getMessage());
            }
            recargarTodosLosInscritos();
            limpiarFormInscripcion();
        } else {
            msg = "⚠ Sin cupo en " + sucNom + ". Niño agregado a lista de espera.";
            lblResultadoCupo.setText("En lista de espera – " + sucNom);
        }
        mostrarAlerta(Alert.AlertType.INFORMATION, "Inscripción", msg);
    }


    private Nino crearNinoPorCategoria(String cat, String nombre, LocalDate fn, int rc, String marca) {
        String c = cat.toLowerCase();
        if (c.contains("acostadito"))
            return new Acostadito(nombre, fn, rc, marca, "1", "NAN", 4, false);
        if (c.contains("aventurero"))
            return new Aventurero(nombre, fn, rc, marca, "2", "NAN", 6, false, 101, "Sin juguete", 9, false);
        if (c.contains("trotamundo"))
            return new Trotamundo(nombre, fn, rc, marca, "2");
        if (c.contains("jugueton"))
            return new Jugueton(nombre, fn, rc, marca, "3");
        if (c.contains("párvulo") || c.contains("parvulo"))
            return new Parvulo(nombre, fn, rc, marca, "3", 1);
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Categoría desconocida."); return null;
    }

    @FXML
    private void onLimpiarInscripcion() { limpiarFormInscripcion(); }

    private void limpiarFormInscripcion() {
        txtNombreNino.clear(); txtRegistroCivil.clear(); txtMarcaPanal.clear();
        txtNombreAcudiente.clear(); txtDocumentoAcudiente.clear();
        txtDireccionAcudiente.clear(); txtTelefonoAcudiente.clear(); txtSalarioAcudiente.clear();
        dpFechaNacimiento.setValue(null);
        cmbCategoriaNino.setValue(null); cmbSucursalNino.setValue(null);
        lblResultadoCupo.setText("Cupo: sin verificar");
    }

    private void recargarTodosLosInscritos() {
        listaInscritos.clear();
        for (Localidad l : guarderia.getLocalidades())
            for (Sucursal s : l.getSucursales())
                for (Nino n : s.getNinos())
                    listaInscritos.add(new NinoFila(n.getNombre(), n.getTipoNino(), s.getNombre(), String.valueOf(n.getRegistroCivil())));
    }

    @FXML
    private void onExportarInscritosCsv() {
        try {
            File f = new File("inscritos.csv");
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("Nombre,Categoria,Sucursal");
                for (NinoFila fila : listaInscritos)
                    pw.println(fila.getNombre() + "," + fila.getCategoria() + "," + fila.getSucursal());
            }
            log("CSV inscritos guardado en: " + f.getAbsolutePath());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exportado", "Archivo: " + f.getAbsolutePath());
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // SERVICIO 2 – DIETA PERSONALIZADA
    // ═════════════════════════════════════════════════════════════════════════
    @FXML
    private void onConsultarDieta() {
        String id = txtIdNinoDieta.getText().trim();

        // Si el campo está vacío, mostrar selector de niños con RC
        if (id.isEmpty()) {
            List<Nino> todos = new ArrayList<>();
            for (Localidad l : guarderia.getLocalidades())
                for (Sucursal s : l.getSucursales())
                    todos.addAll(s.getNinos());

            if (todos.isEmpty()) { mostrarAlerta(Alert.AlertType.WARNING, "Sin niños", "No hay niños inscritos."); return; }

            List<String> opciones = new ArrayList<>();
            for (Nino n : todos)
                opciones.add("RC: " + n.getRegistroCivil() + "  |  " + n.getNombre() + "  (" + n.getTipoNino() + ")");

            ChoiceDialog<String> dialog = new ChoiceDialog<>(opciones.get(0), opciones);
            dialog.setTitle("Seleccionar niño");
            dialog.setHeaderText("Seleccione el niño para ver/editar su dieta:");
            dialog.setContentText("Niño:");
            Optional<String> res = dialog.showAndWait();
            if (res.isEmpty()) return;

            int idx = opciones.indexOf(res.get());
            Nino elegido = todos.get(idx);
            txtIdNinoDieta.setText(String.valueOf(elegido.getRegistroCivil()));
            ninoSeleccionado = elegido;
            txtNombreNinoDieta.setText(elegido.getNombre());
            recargarDieta(elegido);
            return;
        }

        Nino nino = buscarNinoPorRc(id);
        if (nino == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "No encontrado", "Niño con RC " + id + " no encontrado.");
            return;
        }
        ninoSeleccionado = nino;
        txtNombreNinoDieta.setText(nino.getNombre());
        recargarDieta(nino);
    }

    @FXML
    private void onGuardarDieta() {
        if (ninoSeleccionado == null) { mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Consulte primero un niño."); return; }
        String permNom = txtAlimentoPermitido.getText().trim();
        String permCant = txtCantidadPermitida.getText().trim();
        if (!permNom.isEmpty()) {
            ninoSeleccionado.getDieta().agregarAlimento(new Alimento(permNom, permCant.isEmpty() ? "1 porción" : permCant, true, ""));
            txtAlimentoPermitido.clear(); txtCantidadPermitida.clear();
        }
        String prohibNom = txtAlimentoProhibido.getText().trim();
        String razon = (txtRazonProhibicion != null) ? txtRazonProhibicion.getText().trim() : "";
        if (!prohibNom.isEmpty()) {
            ninoSeleccionado.getDieta().agregarAlimento(new Alimento(prohibNom, "-", false, razon.isEmpty() ? "Sin especificar" : razon));
            txtAlimentoProhibido.clear();
            if (txtRazonProhibicion != null) txtRazonProhibicion.clear();
        }
        recargarDieta(ninoSeleccionado);
        mostrarAlerta(Alert.AlertType.INFORMATION, "Guardado", "Dieta actualizada.");
    }

    @FXML
    private void onActualizarDieta() { onGuardarDieta(); }

    private void recargarDieta(Nino nino) {
        listaDieta.clear();
        for (Alimento a : nino.getDieta().getAlimentos())
            listaDieta.add(new AlimentoFila(a.getNombre(), a.getCantidad(),
                    a.isPermitido() ? "Permitido" : "Prohibido", a.getRazonProhibicion()));
    }

    @FXML
    private void onExportarDietaTxt() {
        if (ninoSeleccionado == null) { mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Consulte primero un niño."); return; }
        try {
            File f = new File("dieta_" + ninoSeleccionado.getRegistroCivil() + ".txt");
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("DIETA PERSONALIZADA – " + ninoSeleccionado.getNombre());
                pw.println("RC: " + ninoSeleccionado.getRegistroCivil());
                pw.println("Fecha: " + LocalDate.now());
                pw.println("\n=== ALIMENTOS PERMITIDOS ===");
                for (Alimento a : ninoSeleccionado.getDieta().getAlimentosPermitidos())
                    pw.println("  - " + a.getNombre() + " | " + a.getCantidad());
                pw.println("\n=== ALIMENTOS PROHIBIDOS ===");
                for (Alimento a : ninoSeleccionado.getDieta().getAlimentosProhibidos())
                    pw.println("  - " + a.getNombre() + " | Razón: " + a.getRazonProhibicion());
            }
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exportado", "Archivo: " + f.getAbsolutePath());
        } catch (IOException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage()); }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // SERVICIO 3 – ACTIVIDADES Y SALIDAS
    // ═════════════════════════════════════════════════════════════════════════
    @FXML
    private void onCrearActividad() {
        String nombre = txtNombreActividad.getText().trim();
        String costoStr = txtCostoBase.getText().trim();
        String tipo = cmbTipoActividad.getValue();
        String sucNom = cmbSucursalActividad.getValue();

        if (nombre.isEmpty() || costoStr.isEmpty() || tipo == null || sucNom == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Complete todos los campos.");
            return;
        }

        double costo;
        try { costo = Double.parseDouble(costoStr); } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Costo debe ser numérico."); return;
        }


        actividadSeleccionada = new Actividad(nombre, costo, tipo);
        actividadDAO.agregar(actividadSeleccionada);
        boolean acompana = chkPadreAcompana.isSelected();
        double costoFinal = acompana ? costo * 0.75 : costo;
        double descuento = acompana ? costo * 0.25 : 0;

        listaActividades.add(new ActividadFila(nombre, tipo, sucNom,
                "$" + String.format("%,.0f", costo),
                acompana ? "Sí" : "No",
                "$" + String.format("%,.0f", costoFinal)));

        if (txtCostoFinal != null)  txtCostoFinal.setText(String.format("%.0f", costoFinal));
        if (txtDescuento != null)   txtDescuento.setText(String.format("%.0f", descuento));

        mostrarAlerta(Alert.AlertType.INFORMATION, "Actividad creada",
                "Actividad '" + nombre + "' registrada.\nCosto final: $" + String.format("%,.0f", costoFinal));
    }
    @FXML
    private void onCargarNinosDesdeJson() {
        try {
            File f = new File("ninos.json");
            if (!f.exists()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Sin archivo", "No existe: " + f.getAbsolutePath());
                return;
            }
            List<Nino> ninos = ninoDAO.listar();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Ninos encontrados", "Total: " + ninos.size());
            if (ninos.isEmpty()) return;
            for (Localidad l : guarderia.getLocalidades())
                for (Sucursal s : l.getSucursales())
                    s.getNinos().clear();
            Sucursal s = guarderia.getLocalidades().get(0).getSucursales().get(0);
            for (Nino n : ninos) s.getNinos().add(n);
            recargarTodosLosInscritos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cargado", ninos.size() + " ninos cargados.");
        } catch (Throwable e) {
        mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar",
                e.getClass().getSimpleName() + ": " + e.getMessage());
    }
    }

    @FXML
    private void onCalcularCostoActividad() {
        String costoStr = (txtCostoBase != null) ? txtCostoBase.getText().trim() : "";
        if (costoStr.isEmpty()) { mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese el costo base."); return; }
        try {
            double costo = Double.parseDouble(costoStr);
            boolean acompana = chkPadreAcompana != null && chkPadreAcompana.isSelected();
            double final_ = acompana ? costo * 0.75 : costo;
            if (txtCostoFinal != null) txtCostoFinal.setText(String.format("%.0f", final_));
            if (txtDescuento != null)  txtDescuento.setText(String.format("%.0f", acompana ? costo * 0.25 : 0));
        } catch (NumberFormatException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", "Costo inválido."); }
    }

    @FXML
    private void onAsociarNinoActividad() {
        if (actividadSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Cree primero una actividad.");
            return;
        }

        // Recolectar todos los niños inscritos
        List<Nino> todosLosNinos = new ArrayList<>();
        for (Localidad l : guarderia.getLocalidades())
            for (Sucursal s : l.getSucursales())
                todosLosNinos.addAll(s.getNinos());

        if (todosLosNinos.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin niños", "No hay niños inscritos para asociar.");
            return;
        }

        // Construir lista de opciones legibles
        List<String> opciones = new ArrayList<>();
        for (Nino n : todosLosNinos)
            opciones.add(n.getNombre() + "  (RC: " + n.getRegistroCivil() + " – " + n.getTipoNino() + ")");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(opciones.get(0), opciones);
        dialog.setTitle("Asociar niño");
        dialog.setHeaderText("Actividad: " + actividadSeleccionada.getNombre());
        dialog.setContentText("Seleccione el niño:");

        Optional<String> resultado = dialog.showAndWait();
        if (resultado.isEmpty()) return;

        int idx = opciones.indexOf(resultado.get());
        Nino ninoElegido = todosLosNinos.get(idx);

        boolean acompana = chkPadreAcompana != null && chkPadreAcompana.isSelected();

        // Inscribir el niño en la actividad (usa la lógica del modelo: 25% descuento si acompaña)
        Inscripcion insc = actividadSeleccionada.inscribirNino(ninoElegido, acompana);
        double costoFinal = insc.calcularCosto();

        listaNinosActiv.add(new NinoActividadFila(
                ninoElegido.getNombre(),
                ninoElegido.getTipoNino(),
                acompana ? "Sí" : "No",
                "$" + String.format("%,.0f", costoFinal)));

        mostrarAlerta(Alert.AlertType.INFORMATION, "Niño asociado",
                ninoElegido.getNombre() + " fue asociado a '" + actividadSeleccionada.getNombre() + "'.\n" +
                        "Costo final: $" + String.format("%,.0f", costoFinal) +
                        (acompana ? "  (con 25% descuento)" : ""));
    }

    @FXML
    private void onExportarActividadJson() {
        try {
            File f = new File("actividades.json");
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("[");
                for (int i = 0; i < listaActividades.size(); i++) {
                    ActividadFila a = listaActividades.get(i);
                    pw.println("  {\"nombre\":\"" + a.getNombre() + "\",\"tipo\":\"" + a.getTipo() +
                            "\",\"sucursal\":\"" + a.getSucursal() + "\",\"costoBase\":\"" + a.getCostoBase() + "\"}");
                    if (i < listaActividades.size() - 1) pw.print(",");
                }
                pw.println("]");
            }
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exportado", "Archivo: " + f.getAbsolutePath());
        } catch (IOException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage()); }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // SERVICIO 4 – EVALUACIÓN DE EMPLEADOS
    // ═════════════════════════════════════════════════════════════════════════
    @FXML
    private void onBuscarEmpleado() {
        String id = txtIdEmpleado.getText().trim();

        // Si está vacío, mostrar selector con todos los empleados
        if (id.isEmpty()) {
            List<Empleado> todos = new ArrayList<>();
            for (Localidad l : guarderia.getLocalidades())
                for (Sucursal s : l.getSucursales())
                    todos.addAll(s.getEmpleados());
            if (todos.isEmpty()) { mostrarAlerta(Alert.AlertType.WARNING, "Sin empleados", "No hay empleados registrados."); return; }
            List<String> opciones = new ArrayList<>();
            for (Empleado e : todos)
                opciones.add(e.getNombre() + "  -  CC: " + e.getCedula() + "  -  " + e.getCargo());
            ChoiceDialog<String> dialog = new ChoiceDialog<>(opciones.get(0), opciones);
            dialog.setTitle("Seleccionar empleado");
            dialog.setHeaderText("Empleados registrados:");
            dialog.setContentText("Seleccione:");
            Optional<String> res = dialog.showAndWait();
            if (res.isEmpty()) return;
            Empleado elegido = todos.get(opciones.indexOf(res.get()));
            txtIdEmpleado.setText(String.valueOf(elegido.getCedula()));
            empleadoSeleccionado = elegido;
            txtNombreEmpleado.setText(elegido.getNombre());
            txtExperienciaEmpleado.setText(String.valueOf(elegido.getExperienciaAnios()));
            cmbCargoEmpleado.setValue(elegido.getCargo());
            txtSalarioActual.setText(String.format("%.0f", elegido.getSalario()));
            return;
        }

        Empleado emp = buscarEmpleadoPorCedula(id);
        if (emp == null) {
            String nombre = txtNombreEmpleado.getText().trim();
            String cargo  = cmbCargoEmpleado.getValue();
            if (nombre.isEmpty() || cargo == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "No encontrado",
                        "Empleado no encontrado.\n\nPara crearlo, complete Nombre y Cargo y vuelva a buscar.");
                return;
            }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Empleado no encontrado");
            confirm.setHeaderText(null);
            confirm.setContentText("No existe empleado con cedula " + id + ".\n\n¿Desea crearlo?\n  Nombre: " + nombre + "\n  Cargo: " + cargo);
            Optional<ButtonType> res = confirm.showAndWait();
            if (res.isEmpty() || res.get() != ButtonType.OK) return;
            int experiencia = 0;
            double salario = 0;
            try { experiencia = Integer.parseInt(txtExperienciaEmpleado.getText().trim()); } catch (Exception ignored) {}
            try { salario = Double.parseDouble(txtSalarioActual.getText().trim()); } catch (Exception ignored) {}
            int cedula = 0;
            try { cedula = Integer.parseInt(id); } catch (Exception ignored) {}
            if (salario == 0) salario = 2000000 + (experiencia * 200000L);
            Empleado nuevo = new Empleado(nombre, cedula, LocalDate.now(), "", cargo, 0, cargo, experiencia, false);
            nuevo.setSalario(salario);
            for (Localidad l : guarderia.getLocalidades()) {
                for (Sucursal s : l.getSucursales()) { s.getEmpleados().add(nuevo); break; }
                break;
            }
            empleadoDAO.agregar(nuevo);
            empleadoSeleccionado = nuevo;
            txtNombreEmpleado.setText(nuevo.getNombre());
            txtExperienciaEmpleado.setText(String.valueOf(nuevo.getExperienciaAnios()));
            cmbCargoEmpleado.setValue(nuevo.getCargo());
            txtSalarioActual.setText(String.format("%.0f", nuevo.getSalario()));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Empleado creado",
                    "Empleado '" + nombre + "' creado.\nSalario: $" + String.format("%,.0f", salario));
            return;
        }
        empleadoSeleccionado = emp;
        txtNombreEmpleado.setText(emp.getNombre());
        txtExperienciaEmpleado.setText(String.valueOf(emp.getExperienciaAnios()));
        cmbCargoEmpleado.setValue(emp.getCargo());
        txtSalarioActual.setText(String.format("%.0f", emp.getSalario()));
    }

    @FXML
    private void onCalcularAjusteSalario() {
        if (empleadoSeleccionado == null) { mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Busque primero un empleado."); return; }
        String pStr = txtPuntajeEvaluacion.getText().trim();
        if (pStr.isEmpty()) { mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese el puntaje."); return; }
        try {
            int puntaje = Integer.parseInt(pStr);
            Evaluacion eval = new Evaluacion(LocalDate.now(), puntaje, txtObservacionesEvaluacion.getText().trim());
            double nuevoSalario = empleadoSeleccionado.getSalario() * (1 + eval.getAjusteSalario());
            txtNuevoSalario.setText(String.format("%.0f", nuevoSalario));
        } catch (NumberFormatException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", "Puntaje debe ser numérico."); }
    }

    @FXML
    private void onRegistrarEvaluacion() {
        if (empleadoSeleccionado == null) { mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Busque primero un empleado."); return; }
        String pStr = txtPuntajeEvaluacion.getText().trim();
        String obs   = txtObservacionesEvaluacion.getText().trim();
        if (pStr.isEmpty()) { mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese el puntaje."); return; }
        try {
            int puntaje = Integer.parseInt(pStr);
            Evaluacion eval = new Evaluacion(LocalDate.now(), puntaje, obs);
            empleadoSeleccionado.agregarEvaluacion(eval);
            double nuevoSalario = empleadoSeleccionado.getSalario() * (1 + eval.getAjusteSalario());
            empleadoSeleccionado.setSalario(nuevoSalario);
            empleadoDAO.actualizar(empleadoSeleccionado);
            txtSalarioActual.setText(String.format("%.0f", nuevoSalario));
            listaEvaluaciones.add(new EvaluacionFila(
                    empleadoSeleccionado.getNombre(), empleadoSeleccionado.getCargo(),
                    String.valueOf(puntaje), "$" + String.format("%,.0f", nuevoSalario), obs));
            txtPuntajeEvaluacion.clear(); txtObservacionesEvaluacion.clear();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Evaluación registrada",
                    "Nuevo salario: $" + String.format("%,.0f", nuevoSalario));
        } catch (NumberFormatException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", "Puntaje inválido."); }
    }

    @FXML
    private void onGuardarEmpleadoJson() {
        if (empleadoSeleccionado == null) { mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Busque primero un empleado."); return; }
        try {
            File f = new File("empleado_" + empleadoSeleccionado.getCedula() + ".json");
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("{");
                pw.println("  \"nombre\": \"" + empleadoSeleccionado.getNombre() + "\",");
                pw.println("  \"cedula\": " + empleadoSeleccionado.getCedula() + ",");
                pw.println("  \"cargo\": \"" + empleadoSeleccionado.getCargo() + "\",");
                pw.println("  \"salario\": " + empleadoSeleccionado.getSalario() + ",");
                pw.println("  \"experiencia\": " + empleadoSeleccionado.getExperienciaAnios());
                pw.println("}");
            }
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exportado", "Archivo: " + f.getAbsolutePath());
        } catch (IOException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage()); }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // SERVICIO 5 – CONSOLIDADO FINANCIERO
    // ═════════════════════════════════════════════════════════════════════════
    @FXML
    private void onCalcularConsolidado() {
        listaConsolidado.clear();
        double totIng = 0, totGas = 0;
        for (Localidad l : guarderia.getLocalidades()) {
            for (Sucursal s : l.getSucursales()) {
                double ing = 0;
                try { ing += Double.parseDouble(safeText(txtIngresosMatriculas)); } catch (Exception ignored) {}
                try { ing += Double.parseDouble(safeText(txtIngresosPensiones)); } catch (Exception ignored) {}
                try { ing += Double.parseDouble(safeText(txtIngresosJornadas)); }  catch (Exception ignored) {}
                try { ing += Double.parseDouble(safeText(txtIngresosExtras)); }    catch (Exception ignored) {}
                double gas = 0;
                try { gas = Double.parseDouble(safeText(txtGastosSucursal)); }     catch (Exception ignored) {}
                s.setIngresos(s.getIngresos() + ing);
                s.setGastos(s.getGastos() + gas);
                double bal = s.getIngresos() - s.getGastos();
                listaConsolidado.add(new ConsolidadoFila(s.getNombre(),
                        "$" + String.format("%,.0f", s.getIngresos()),
                        "$" + String.format("%,.0f", s.getGastos()),
                        "$" + String.format("%,.0f", bal)));
                totIng += s.getIngresos();
                totGas += s.getGastos();
            }
        }
        if (txtTotalIngresos != null) txtTotalIngresos.setText(String.format("%.0f", totIng));
        if (txtTotalGastos != null)   txtTotalGastos.setText(String.format("%.0f", totGas));
        if (txtBalance != null)       txtBalance.setText(String.format("%.0f", totIng - totGas));
    }

    @FXML
    private void onExportarConsolidadoCsv() {
        try {
            File f = new File("consolidado.csv");
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("Sucursal,Ingresos,Gastos,Balance");
                for (ConsolidadoFila fila : listaConsolidado)
                    pw.println(fila.getSucursal() + "," + fila.getIngresos() + "," + fila.getGastos() + "," + fila.getBalance());
            }
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exportado", "Archivo: " + f.getAbsolutePath());
        } catch (IOException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage()); }
    }

    @FXML
    private void onGenerarResumenTxt() {
        try {
            File f = new File("resumen_financiero.txt");
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("CONSOLIDADO FINANCIERO – ADSw-AGUGU");
                pw.println("Fecha: " + LocalDate.now());
                pw.println("─────────────────────────────────");
                for (ConsolidadoFila fila : listaConsolidado)
                    pw.printf("%-25s  Ing: %-12s  Gas: %-12s  Bal: %s%n",
                            fila.getSucursal(), fila.getIngresos(), fila.getGastos(), fila.getBalance());
                pw.println("─────────────────────────────────");
                pw.println("Total ingresos: " + safeText(txtTotalIngresos));
                pw.println("Total gastos:   " + safeText(txtTotalGastos));
                pw.println("Balance total:  " + safeText(txtBalance));
            }
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exportado", "Archivo: " + f.getAbsolutePath());
        } catch (IOException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage()); }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // SERVICIO 6 – CARNÉ DE VACUNAS
    // ═════════════════════════════════════════════════════════════════════════
    @FXML
    private void onActualizarCarne() {
        String id = txtIdNinoVacuna.getText().trim();
        if (id.isEmpty()) { mostrarAlerta(Alert.AlertType.WARNING, "Error", "Ingrese ID del niño."); return; }
        Nino nino = buscarNinoPorRc(id);
        if (nino == null) { mostrarAlerta(Alert.AlertType.WARNING, "No encontrado", "Niño no encontrado."); return; }
        ninoSeleccionado = nino;
        txtNombreNinoVacuna.setText(nino.getNombre());
        recargarCarneVacunas(nino);
    }

    @FXML
    private void onRegistrarDosis() {
        if (ninoSeleccionado == null) { mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Actualice primero el carné del niño."); return; }
        String enfermedad = txtEnfermedad.getText().trim();
        String lab        = txtLaboratorio.getText().trim();
        String serialStr  = txtSerialVacuna.getText().trim();
        LocalDate fecha   = (dpFechaDosis.getValue() != null) ? dpFechaDosis.getValue() : LocalDate.now();

        if (enfermedad.isEmpty() || lab.isEmpty() || serialStr.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Complete enfermedad, laboratorio y serial.");
            return;
        }
        long serial;
        try { serial = Long.parseLong(serialStr); } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Serial debe ser numérico."); return;
        }
        boolean primeraVez = chkPrimeraVez != null && chkPrimeraVez.isSelected();
        boolean privada    = chkVacunaPrivada != null && chkVacunaPrivada.isSelected();
        int num = primeraVez ? 1 : 2;

        Dosis dosis = new Dosis(enfermedad, fecha, num, lab, serial, !privada, false);
        ninoSeleccionado.getCarne().agregarDosis(dosis);
        recargarCarneVacunas(ninoSeleccionado);

        txtEnfermedad.clear(); txtLaboratorio.clear(); txtSerialVacuna.clear();
        if (chkPrimeraVez != null) chkPrimeraVez.setSelected(false);
        mostrarAlerta(Alert.AlertType.INFORMATION, "Dosis registrada",
                "Vacuna contra " + enfermedad + " registrada para " + ninoSeleccionado.getNombre());
    }

    private void recargarCarneVacunas(Nino nino) {
        listaDosis.clear();
        for (Dosis d : nino.getCarne().getDosis())
            listaDosis.add(new DosisFila(d.getEnfermedad(), d.getFecha().toString(),
                    d.esPrimeraVez() ? "Primera vez" : "Refuerzo " + d.getNumeroAplicacion(),
                    d.getLaboratorio(), String.valueOf(d.getSerial())));
    }

    @FXML
    private void onConsultarPendientesVacuna() {
        listaPendientes.clear();
        String vacunaBuscar = (cmbTipoVacuna != null && cmbTipoVacuna.getValue() != null)
                ? cmbTipoVacuna.getValue() : "";
        for (Localidad l : guarderia.getLocalidades())
            for (Sucursal s : l.getSucursales())
                for (Nino n : s.getNinos())
                    if (vacunaBuscar.isEmpty() || !n.getCarne().tieneVacuna(vacunaBuscar))
                        listaPendientes.add(new PendienteFila(n.getNombre(),
                                vacunaBuscar.isEmpty() ? "Todas" : vacunaBuscar,
                                n.getEdadEnMeses() + "m", s.getNombre(),
                                String.valueOf(n.getRegistroCivil())));
        if (listaPendientes.isEmpty())
            mostrarAlerta(Alert.AlertType.INFORMATION, "Pendientes",
                    "No hay niños pendientes de vacunación para el filtro seleccionado.");
    }

    @FXML
    private void onExportarPendientesJson() {
        try {
            File f = new File("pendientes_vacunacion.json");
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("[");
                for (int i = 0; i < listaPendientes.size(); i++) {
                    PendienteFila p = listaPendientes.get(i);
                    pw.println("  {\"nino\":\"" + p.getNino() + "\",\"vacuna\":\"" + p.getVacuna() +
                            "\",\"edad\":\"" + p.getEdad() + "\",\"sucursal\":\"" + p.getSucursal() + "\"}");
                    if (i < listaPendientes.size() - 1) pw.print(",");
                }
                pw.println("]");
            }
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exportado", "Archivo: " + f.getAbsolutePath());
        } catch (IOException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage()); }
    }

    @FXML
    private void onGenerarGraficoTorta() {
        long total = 0, pendientes = 0;
        String vac = (cmbTipoVacuna != null && cmbTipoVacuna.getValue() != null) ? cmbTipoVacuna.getValue() : "BCG";
        for (Localidad l : guarderia.getLocalidades())
            for (Sucursal s : l.getSucursales())
                for (Nino n : s.getNinos()) {
                    total++;
                    if (!n.getCarne().tieneVacuna(vac)) pendientes++;
                }
        if (total == 0) { mostrarAlerta(Alert.AlertType.WARNING, "Sin datos", "No hay niños registrados."); return; }
        double pct = (pendientes * 100.0) / total;
        mostrarAlerta(Alert.AlertType.INFORMATION, "Resumen vacunación – " + vac,
                String.format("Total niños: %d%nVacunados: %d (%.1f%%)%nPendientes: %d (%.1f%%)",
                        total, total - pendientes, 100 - pct, pendientes, pct));
    }

    // ═════════════════════════════════════════════════════════════════════════
    // SERVICIO 7 – SERIALIZACIÓN / DESERIALIZACIÓN
    // ═════════════════════════════════════════════════════════════════════════
    @FXML
    private void onGuardarEstadoSistema() {
        String ruta = safeText(txtRutaArchivoDat);
        if (ruta.isEmpty()) ruta = "guarderia_estado.dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(guarderia);
            lblEstadoSerializacion.setText("Estado guardado: " + ruta);
            log("✓ Serialización exitosa → " + ruta);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Guardado", "Estado del sistema serializado en:\n" + ruta);
        } catch (IOException e) {
            lblEstadoSerializacion.setText("Error al guardar.");
            log("✗ Error: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void onRestaurarEstadoSistema() {
        String ruta = safeText(txtRutaArchivoDat);
        if (ruta.isEmpty()) ruta = "guarderia_estado.dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            guarderia = (Guarderia) ois.readObject();
            lblEstadoSerializacion.setText("Estado restaurado: " + ruta);
            recargarTodosLosInscritos();
            log("✓ Deserialización exitosa ← " + ruta);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Restaurado", "Estado del sistema cargado desde:\n" + ruta);
        } catch (IOException | ClassNotFoundException e) {
            lblEstadoSerializacion.setText("Error al restaurar.");
            log("✗ Error: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void onGuardarFichaJson() {
        if (ninoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Consulte primero un niño en la sección de Dieta o Vacunas."); return;
        }
        try {
            File f = new File("ficha_" + ninoSeleccionado.getRegistroCivil() + ".json");
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("{");
                pw.println("  \"nombre\": \"" + ninoSeleccionado.getNombre() + "\",");
                pw.println("  \"registroCivil\": " + ninoSeleccionado.getRegistroCivil() + ",");
                pw.println("  \"tipo\": \"" + ninoSeleccionado.getTipoNino() + "\",");
                pw.println("  \"edadMeses\": " + ninoSeleccionado.getEdadEnMeses() + ",");
                pw.println("  \"marcaPañal\": \"" + ninoSeleccionado.getMarcaPañal() + "\"");
                pw.println("}");
            }
            log("✓ Ficha JSON guardada → " + f.getAbsolutePath());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Guardado", "Ficha: " + f.getAbsolutePath());
        } catch (IOException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage()); }
    }

    @FXML
    private void onGenerarBackupJson() {
        String ruta = safeText(txtRutaBackupJson);
        if (ruta.isEmpty()) ruta = "backup_" + LocalDate.now() + ".json";
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            pw.println("{\"guarderia\":\"" + guarderia.getNombre() + "\",\"fecha\":\"" + LocalDate.now() + "\",\"localidades\":[");
            boolean primL = true;
            for (Localidad l : guarderia.getLocalidades()) {
                if (!primL) pw.print(",");
                pw.print("{\"nombre\":\"" + l.getNombre() + "\",\"totalNinos\":" + l.getTotalNinos() + "}");
                primL = false;
            }
            pw.println("]}");
            log("✓ Backup JSON generado → " + ruta);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Backup generado", "Archivo: " + ruta);
        } catch (IOException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage()); }
    }

    @FXML
    private void onSeleccionarArchivo() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar archivo");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos DAT", "*.dat"),
                new FileChooser.ExtensionFilter("Todos", "*.*"));
        File f = fc.showOpenDialog(null);
        if (f != null && txtRutaArchivoDat != null) txtRutaArchivoDat.setText(f.getAbsolutePath());
    }

    @FXML
    private void onSerializarCarne() {
        if (ninoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Consulte primero un niño en la sección de Vacunas."); return;
        }
        String ruta = "carne_" + ninoSeleccionado.getRegistroCivil() + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(ninoSeleccionado.getCarne());
            log("✓ Carné serializado → " + ruta);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Serializado",
                    "Carné de " + ninoSeleccionado.getNombre() + " guardado en:\n" + ruta);
        } catch (IOException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage()); }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // UTILIDADES INTERNAS
    // ═════════════════════════════════════════════════════════════════════════
    private Sucursal buscarSucursal(String nombre) {
        for (Localidad l : guarderia.getLocalidades())
            for (Sucursal s : l.getSucursales())
                if (s.getNombre().equals(nombre)) return s;
        return null;
    }

    private Nino buscarNinoPorRc(String rc) {
        for (Localidad l : guarderia.getLocalidades())
            for (Sucursal s : l.getSucursales())
                for (Nino n : s.getNinos())
                    if (String.valueOf(n.getRegistroCivil()).equals(rc) ||
                            n.getNombre().equalsIgnoreCase(rc)) return n;
        return null;
    }

    private Empleado buscarEmpleadoPorCedula(String cedStr) {
        for (Localidad l : guarderia.getLocalidades())
            for (Sucursal s : l.getSucursales())
                for (Empleado e : s.getEmpleados())
                    if (String.valueOf(e.getCedula()).equals(cedStr) ||
                            e.getNombre().equalsIgnoreCase(cedStr)) return e;
        return null;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private String safeText(TextField tf) { return (tf != null) ? tf.getText().trim() : ""; }

    private void log(String msg) {
        if (txtLogSerializacion != null)
            txtLogSerializacion.appendText("[" + LocalDate.now() + "] " + msg + "\n");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // INNER CLASSES – filas para TableView (JavaFX necesita propiedades String)
    // ═════════════════════════════════════════════════════════════════════════
    public static class NinoFila {
        private final String nombre, categoria, sucursal, rc;
        public NinoFila(String n, String c, String s, String r){ nombre=n; categoria=c; sucursal=s; rc=r; }
        public String getNombre()   { return nombre; }
        public String getCategoria(){ return categoria; }
        public String getSucursal() { return sucursal; }
        public String getRc()       { return rc; }
    }

    public static class AlimentoFila {
        private final String nombre, cantidad, tipo, razon;
        public AlimentoFila(String n, String c, String t, String r){ nombre=n; cantidad=c; tipo=t; razon=r; }
        public String getNombre()   { return nombre; }
        public String getCantidad() { return cantidad; }
        public String getTipo()     { return tipo; }
        public String getRazon()    { return razon; }
    }

    public static class ActividadFila {
        private final String nombre, tipo, sucursal, costoBase, acompanante, costoFinal;
        public ActividadFila(String n, String t, String s, String cb, String a, String cf){
            nombre=n; tipo=t; sucursal=s; costoBase=cb; acompanante=a; costoFinal=cf; }
        public String getNombre()     { return nombre; }
        public String getTipo()       { return tipo; }
        public String getSucursal()   { return sucursal; }
        public String getCostoBase()  { return costoBase; }
        public String getAcompanante(){ return acompanante; }
        public String getCostoFinal() { return costoFinal; }
    }

    public static class NinoActividadFila {
        private final String nombre, categoria, acompanante, costoFinal;
        public NinoActividadFila(String n, String c, String a, String cf){
            nombre=n; categoria=c; acompanante=a; costoFinal=cf; }
        public String getNombre()     { return nombre; }
        public String getCategoria()  { return categoria; }
        public String getAcompanante(){ return acompanante; }
        public String getCostoFinal() { return costoFinal; }
    }

    public static class EvaluacionFila {
        private final String empleado, cargo, puntaje, nuevoSalario, observaciones;
        public EvaluacionFila(String e, String c, String p, String ns, String o){
            empleado=e; cargo=c; puntaje=p; nuevoSalario=ns; observaciones=o; }
        public String getEmpleado()     { return empleado; }
        public String getCargo()        { return cargo; }
        public String getPuntaje()      { return puntaje; }
        public String getNuevoSalario() { return nuevoSalario; }
        public String getObservaciones(){ return observaciones; }
    }

    public static class ConsolidadoFila {
        private final String sucursal, ingresos, gastos, balance;
        public ConsolidadoFila(String s, String i, String g, String b){
            sucursal=s; ingresos=i; gastos=g; balance=b; }
        public String getSucursal() { return sucursal; }
        public String getIngresos() { return ingresos; }
        public String getGastos()   { return gastos; }
        public String getBalance()  { return balance; }
    }

    public static class DosisFila {
        private final String enfermedad, fecha, tipoDosis, laboratorio, serial;
        public DosisFila(String en, String fe, String td, String la, String se){
            enfermedad=en; fecha=fe; tipoDosis=td; laboratorio=la; serial=se; }
        public String getEnfermedad() { return enfermedad; }
        public String getFecha()      { return fecha; }
        public String getTipoDosis()  { return tipoDosis; }
        public String getLaboratorio(){ return laboratorio; }
        public String getSerial()     { return serial; }
    }

    public static class PendienteFila {
        private final String nino, vacuna, edad, sucursal, rc;
        public PendienteFila(String n, String v, String e, String s, String r){
            nino=n; vacuna=v; edad=e; sucursal=s; rc=r; }
        public String getNino()     { return nino; }
        public String getVacuna()   { return vacuna; }
        public String getEdad()     { return edad; }
        public String getSucursal() { return sucursal; }
        public String getRc()       { return rc; }
    }
    @FXML
    private void onBuscarNinoVacunaPorId() { onActualizarCarne(); }
    @FXML
    private void onTipoVacunaSeleccionada() {
        if (cmbTipoVacuna != null && cmbTipoVacuna.getValue() != null && txtEnfermedad != null)
            txtEnfermedad.setText(cmbTipoVacuna.getValue());
    }
    @FXML
    private void onCargarEmpleadosDesdeJson() {
        List<Empleado> empleados = empleadoDAO.listar();
        if (empleados.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin datos", "No hay empleados en empleados.json."); return;
        }
        Sucursal s = guarderia.getLocalidades().get(0).getSucursales().get(0);
        s.getEmpleados().clear();
        s.getEmpleados().addAll(empleados);
        log("Cargados " + empleados.size() + " empleados.");
        mostrarAlerta(Alert.AlertType.INFORMATION, "Cargado", empleados.size() + " empleados cargados.");
    }

    @FXML
    private void onCargarActividadesDesdeCSV() {
        List<Actividad> actividades = actividadDAO.listar();
        if (actividades.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin datos", "No hay actividades en actividades.csv."); return;
        }
        listaActividades.clear();
        for (Actividad a : actividades)
            listaActividades.add(new ActividadFila(a.getNombre(), a.getDescripcion(),
                    "-", "$" + String.format("%,.0f", a.getCostoBase()), "No",
                    "$" + String.format("%,.0f", a.getCostoBase())));
        log("Cargadas " + actividades.size() + " actividades.");
        mostrarAlerta(Alert.AlertType.INFORMATION, "Cargado", actividades.size() + " actividades cargadas.");
    }
    @FXML
    private void onSerializarCarneDesdeSistema() {
        List<Nino> todos = new ArrayList<>();
        for (Localidad l : guarderia.getLocalidades())
            for (Sucursal s : l.getSucursales()) todos.addAll(s.getNinos());
        if (todos.isEmpty()) { mostrarAlerta(Alert.AlertType.WARNING, "Sin ninos", "No hay ninos inscritos."); return; }
        List<String> opciones = new ArrayList<>();
        for (Nino n : todos) opciones.add("RC: " + n.getRegistroCivil() + "  |  " + n.getNombre());
        ChoiceDialog<String> dialog = new ChoiceDialog<>(opciones.get(0), opciones);
        dialog.setTitle("Serializar carne"); dialog.setHeaderText("Seleccione el nino:"); dialog.setContentText("Nino:");
        Optional<String> res = dialog.showAndWait();
        if (res.isEmpty()) return;
        Nino elegido = todos.get(opciones.indexOf(res.get()));
        String ruta = "carne_" + elegido.getRegistroCivil() + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(elegido.getCarne());
            log("Carne serializado -> " + ruta);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Serializado", "Carne guardado:\n" + ruta);
        } catch (IOException e) { mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage()); }
    }
    @FXML
    private void onLimpiarLogSerializacion() {
        if (txtLogSerializacion != null) txtLogSerializacion.clear();
    }
}
