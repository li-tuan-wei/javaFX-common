package ldh.fx.component.table;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import ldh.common.PageResult;
import ldh.common.Pagination;
import ldh.fx.component.table.function.LoadData;
import ldh.fx.util.DialogUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by ldh on 2017/4/13.
 */
public class PageablePane extends HBox {

    private final static String FORMAT = "数据从 %s 到 %s， 总共 %s 条";

    private LoadData loadData;
    private PageResult pageResult;

    @FXML private Label totalPageLabel;
    @FXML private TextField currentPageText;
    @FXML private ComboBox<Integer> pageSizeChoiceBox;
    @FXML private ProgressBar progressBar;
    @FXML private Button firstBtn;
    @FXML private Button preBtn;
    @FXML private Button nextBtn;
    @FXML private Button lastBtn;
    @FXML private Button refreshBtn;
    @FXML private Label paginationInfo;

    public PageablePane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/component/PageablePane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        pageSizeChoiceBox.getItems().addAll(5, 10, 20, 50);
        pageSizeChoiceBox.getSelectionModel().select(1);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);
        this.setPadding(new Insets(5));
        progressBar.setProgress(0d);
        this.setMaxWidth(Integer.MAX_VALUE);
        progressBar.setVisible(false);

        event();
    }

    private void event() {
        pageSizeChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue observable, Integer oldValue, Integer newValue) {
                if (newValue != null && !newValue.equals(oldValue) && oldValue != null) {
                    Pagination pagination = new Pagination(1, newValue);
                    loadData.load(pagination);
                }
            }
        });
    }

    public void setPageSizes(List<Integer> pageSizes) {
        pageSizeChoiceBox.getItems().addAll(pageSizes);
    }

    public void setPageSize(Integer pageSize) {
        pageSizeChoiceBox.getSelectionModel().select(pageSize);
    }

    public void setPageResult(PageResult pageResult) {
        this.pageResult = pageResult;
        totalPageLabel.setText(pageResult.getPageTotal() + "");
        currentPageText.setText(pageResult.getPageNo() + "");
        if(pageResult.getPageNo() == 1) {
            firstBtn.setDisable(true);
            preBtn.setDisable(true);
        } else {
            firstBtn.setDisable(false);
            preBtn.setDisable(false);
        }
        if(pageResult.getPageNo() == pageResult.getPageTotal()) {
            lastBtn.setDisable(true);
            nextBtn.setDisable(true);
        } else {
            lastBtn.setDisable(false);
            nextBtn.setDisable(false);
        }
        if (pageResult.getPageNo() == pageResult.getPageTotal() && pageResult.getPageNo() == 1) {
            currentPageText.setDisable(true);
        } else {
            currentPageText.setDisable(false);
        }
        long idx = (pageResult.getPageNo() - 1) * pageResult.getPageSize();
        paginationInfo.setText(String.format(FORMAT,  idx + 1, idx + pageResult.getBeans().size() - 1, pageResult.getTotal()));
    }

    public void setLoadData(LoadData loadData) {
        this.loadData = loadData;
    }

    @FXML
    private void firstPageAct() {
        if(checkNull()) return;
        Pagination pagination = new Pagination(1, pageResult.getPageSize());
        loadData.load(pagination);
    }

    @FXML
    private void prePageAct() {
        if(checkNull()) return;
        long pageNo = pageResult.getPageNo();
        if(pageNo < 2) return;
        pageNo += -1;
        Pagination pagination = new Pagination(pageNo, pageResult.getPageSize());
        loadData.load(pagination);
    }

    @FXML
    private void pageAct() {
        if(checkNull()) return;
        Long num = Long.parseLong(currentPageText.getText().trim());
        if (num != pageResult.getPageNo() && num < pageResult.getPageTotal()) {
            Pagination pagination = new Pagination(num, pageResult.getPageSize());
            loadData.load(pagination);
        }
    }

    @FXML
    private void nextPageAct() {
        if(checkNull()) return;
        long pageNo = pageResult.getPageNo();
        if(pageNo >= pageResult.getPageTotal()) return;
        pageNo += 1;
        Pagination pagination = new Pagination(pageNo, pageResult.getPageSize());
        loadData.load(pagination);
    }

    @FXML
    private void lastPageAct() {
        if(checkNull()) return;
        long pageNo = pageResult.getPageNo();
        if(pageNo >= pageResult.getPageTotal()) return;
        pageNo = pageResult.getPageTotal();
        Pagination pagination = new Pagination(pageNo, pageResult.getPageSize());
        loadData.load(pagination);
    }

    @FXML
    private void refreshPageAct() {
        if (pageResult == null) {
            pageResult = new PageResult(0, null);
        }
        Pagination pagination = new Pagination(pageResult.getPageNo(), pageResult.getPageSize());
        loadData.load(pagination);
    }


    public void setLoading(boolean isLoading) {
        pageSizeChoiceBox.setDisable(isLoading);
        currentPageText.setDisable(isLoading);
        firstBtn.setDisable(isLoading);
        preBtn.setDisable(isLoading);
        nextBtn.setDisable(isLoading);
        lastBtn.setDisable(isLoading);
    }

    private boolean checkNull() {
        if (pageResult == null) {
            DialogUtil.info("数据为空", "错误", 300, 200);
            return true;
        }
        return false;
    }

}
