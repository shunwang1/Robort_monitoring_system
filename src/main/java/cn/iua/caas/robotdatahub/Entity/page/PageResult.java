package cn.iua.caas.robotdatahub.Entity.page;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResult<T> {
    private List<T> records;
    private int total;
    private int currentPage;
    private int pageSize;
}