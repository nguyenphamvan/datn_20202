package com.nguyenpham.oganicshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLoggingDto {

    private long orderId;
    private String latestStatus;
    private String lastUpdatedTime;
    private Set<LoggingOrderStatus> loggingStatus;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoggingOrderStatus implements Comparable<LoggingOrderStatus>{
        private String status;
        private String updatedTime;

        @Override
        public int compareTo(LoggingOrderStatus o) {
            return this.getUpdatedTime().compareTo(o.getUpdatedTime());
        }
    }

}
