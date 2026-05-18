package com.company.shared.dto;

import java.util.Map;

public class ApiResponse<T> {
    private T data;
    private boolean success;
    private String message;
    private PaginationMeta pagination;

    public ApiResponse() {}

    public ApiResponse(T data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, true, "Success");
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, false, message);
    }

    public static <T> ApiResponse<T> paginated(T data, PaginationMeta pagination) {
        ApiResponse<T> response = new ApiResponse<>(data, true, "Success");
        response.setPagination(pagination);
        return response;
    }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public PaginationMeta getPagination() { return pagination; }
    public void setPagination(PaginationMeta pagination) { this.pagination = pagination; }

    public static class PaginationMeta {
        private int page;
        private int size;
        private long total;
        private int totalPages;

        public PaginationMeta(int page, int size, long total, int totalPages) {
            this.page = page;
            this.size = size;
            this.total = total;
            this.totalPages = totalPages;
        }

        public int getPage() { return page; }
        public int getSize() { return size; }
        public long getTotal() { return total; }
        public int getTotalPages() { return totalPages; }
    }
}
