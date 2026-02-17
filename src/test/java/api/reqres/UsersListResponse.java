package api.reqres;

import java.util.ArrayList;
import java.util.List;

import io.restassured.response.Response;

public class UsersListResponse {

    private final int total;
    private final int totalPages;
    private final List<Integer> userIds;

    private UsersListResponse(int total, int totalPages, List<Integer> userIds) {
        this.total = total;
        this.totalPages = totalPages;
        this.userIds = userIds;
    }

    public static UsersListResponse from(Response r) {
        Integer total = r.path("total");
        Integer totalPages = r.path("total_pages");

        List<Integer> ids = r.path("data.id");
        if (ids == null) {
            ids = new ArrayList<Integer>();
        }

        return new UsersListResponse(total.intValue(), totalPages.intValue(), ids);
    }

    public int getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }
}
