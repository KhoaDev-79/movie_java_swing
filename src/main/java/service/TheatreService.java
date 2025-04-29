package service;

import java.util.List;
import dao.TheatreDao;
import model.Theatre;

public class TheatreService {
    private TheatreDao theatreDao;

    public TheatreService() {
        this.theatreDao = new TheatreDao();
    }

    // Thêm phòng chiếu mới
    public boolean addTheatre(Theatre theatre) {
        return theatreDao.addTheatre(theatre);
    }

    // Sửa thông tin phòng chiếu
    public boolean updateTheatre(Theatre theatre) {
        return theatreDao.updateTheatre(theatre);
    }

    // Xóa phòng chiếu
    public boolean deleteTheatre(int theatreID) {
        return theatreDao.deleteTheatre(theatreID);
    }

    // Lấy danh sách phòng chiếu
    public List<Theatre> getAllTheatres() {
        return theatreDao.getAllTheatres();
    }
}
