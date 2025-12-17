package udn.vku.greenstayapp.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import udn.vku.greenstayapp.model.Homestay;
import udn.vku.greenstayapp.service.HomestayService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.Button;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogController {

    @FXML private TableView<Homestay> tableHomestay;
    @FXML private TableColumn<Homestay, Integer> colId;
    @FXML private TableColumn<Homestay, String> colName;
    @FXML private TableColumn<Homestay, String> colAddress;
    @FXML private TableColumn<Homestay, Double> colPrice;
    @FXML private TableColumn<Homestay, Boolean> colEco;
    @FXML private TextField tfSearch;
    @FXML private TableColumn<Homestay, Void> colAction;


    private HomestayService service;


    public void initialize() {
        service = new HomestayService();

        // Map các cột với thuộc tính trong Model
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colEco.setCellValueFactory(new PropertyValueFactory<>("ecoCertified"));
        addButtonToTable();

        // Load dữ liệu ban đầu
        loadData(service.getAllHomestays());
    }

    private void addButtonToTable() {
        Callback<TableColumn<Homestay, Void>, TableCell<Homestay, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Homestay, Void> call(final TableColumn<Homestay, Void> param) {
                final TableCell<Homestay, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Đặt ngay");

                    {
                        btn.setOnAction((event) -> {
                            Homestay data = getTableView().getItems().get(getIndex());
                            openBookingForm(data); // Hàm mở form đặt phòng
                        });
                        btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colAction.setCellFactory(cellFactory);
    }

    private void openBookingForm(Homestay selectedHomestay) {
        try {

            System.out.println("Đang đặt phòng tại: " + selectedHomestay.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearch() {
        String keyword = tfSearch.getText().toLowerCase();

        // Lấy tất cả danh sách gốc
        List<Homestay> allList = service.getAllHomestays();

        // Lọc bằng Java Stream (Tìm theo tên HOẶC địa chỉ)
        List<Homestay> filteredList = allList.stream()
                .filter(h -> h.getName().toLowerCase().contains(keyword) ||
                        h.getAddress().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        // Cập nhật lại bảng
        ObservableList<Homestay> data = FXCollections.observableArrayList(filteredList);
        tableHomestay.setItems(data);
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
    public void handleLogout() {
        try {
            Stage currentStage = (Stage) tableHomestay.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/udn/vku/greenstayapp/LoginView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("GreenStay - Đăng nhập");
            currentStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
