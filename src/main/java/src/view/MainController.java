package src.view;

import src.controller.Empleado;
import src.controller.GestorTareas;

public class MainController {
    private MainView mainView;
    private Empleado empleado;
    private GestorTareas gestorTareas;
    
    public MainController(MainView mainView, Empleado empleado) {
        this.mainView = mainView;
        this.empleado = empleado;
        this.gestorTareas = new GestorTareas();
        setupView();
        setupHandlers();
    }
    
    private void setupView() {
        String welcomeMsg = "Bienvenido, Empleado " + empleado.getId();
        if (empleado.getRol() != null) {
            welcomeMsg += " (rol: " + empleado.getRol() + ")";
        }
        mainView.setWelcomeMessage(welcomeMsg);
    }
    
    private void setupHandlers() {
        mainView.getAgregarButton().setOnAction(e -> showAgregarTareaDialog());
        mainView.getEditarButton().setOnAction(e -> showEditarTareaDialog());
        mainView.getEliminarButton().setOnAction(e -> showEliminarTareaDialog());
        mainView.getListarButton().setOnAction(e -> mostrarTareas());
        mainView.getBuscarButton().setOnAction(e -> showBuscarTareaDialog());
    }
    
    private void showAgregarTareaDialog() {
        AgregarTareaDialog dialog = new AgregarTareaDialog(mainView.getStage());
        dialog.show();
    }
    
    private void showEditarTareaDialog() {
        EditarTareaDialog dialog = new EditarTareaDialog(mainView.getStage(), gestorTareas);
        dialog.show();
    }
    
    private void showEliminarTareaDialog() {
        EliminarTareaDialog dialog = new EliminarTareaDialog(mainView.getStage(), gestorTareas);
        dialog.show();
    }
    
    private void mostrarTareas() {
        TareasListView listView = new TareasListView(mainView.getStage(), gestorTareas);
        listView.show();
    }
    
    private void showBuscarTareaDialog() {
        BuscarTareaDialog dialog = new BuscarTareaDialog(mainView.getStage(), gestorTareas);
        dialog.show();
    }
    
    public GestorTareas getGestorTareas() {
        return gestorTareas;
    }
}

