/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dto;

import java.util.List;

/**
 *
 * @author Wael M Elmahask
 */
public class Results<T> {

    private int page;
    private Long totalResults;
    private Long totalPages;
    private List<T> results;

    public Results(int page, Long totalResults, Long totalPages, List<T> results) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public Results() {}

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        Long total = totalPages / 10;
        if ((totalPages % 10) > 0) {
            System.out.println("Sum = " + total);
            total += 1;
        }
        this.totalPages = total;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

}
