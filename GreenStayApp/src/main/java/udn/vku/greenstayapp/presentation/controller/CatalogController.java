package udn.vku.greenstayapp.presentation.controller;

import udn.vku.greenstayapp.model.Homestay;
import udn.vku.greenstayapp.service.HomestayService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CatalogController {

    @FXML private TableView<Homestay> tableHomestay;
    @FXML private TableColumn<Homestay, Integer> colId;
    @FXML private TableColumn<Homestay, String> colName;
    @FXML private TableColumn<Homestay, String> colAddress;
    @FXML private TableColumn<Homestay, Double> colPrice;
    @FXML private TableColumn<Homestay, Boolean> colEco;

    private HomestayService service;

    public void initialize() {
        service = new HomestayService();

        // Map các cột với thuộc tính trong Model
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colEco.setCellValueFactory(new PropertyValueFactory<>("ecoCertified"));

        // Load dữ liệu ban đầu
        loadData(service.getAllHomestays());
    }

    @FXML
    private void handleShowAll() {
        loadData(service.getAllHomestays());
    }

    @FXML
    private void handleShowEco() {
        loadData(service.getEcoFriendlyHomestays());
    }

    private void loadData(java.util.List<Homestay> list) {
        ObservableList<Homestay> data = FXCollections.observableArrayList(list);
        tableHomestay.setItems(data);
    }
}
