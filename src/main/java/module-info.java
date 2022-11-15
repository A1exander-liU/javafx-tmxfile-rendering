module com.example.tiledmaptest {
    requires javafx.controls;
    requires javafx.fxml;
    requires TiledReader;
    requires java.desktop;
    requires org.dyn4j;

    opens com.example.tiledmaptest to javafx.fxml;
    exports com.example.tiledmaptest;
    exports com.example.tiledmaptest.starter;
    opens com.example.tiledmaptest.starter to javafx.fxml;
    exports com.example.tiledmaptest.tests;
    opens com.example.tiledmaptest.tests to javafx.fxml;
}