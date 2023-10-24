package sample.cafekiosk.spring.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

public abstract class BaseEntity {

	@CreatedDate
	private LocalDateTime createdDateTime;

	@LastModifiedDate
	private LocalDateTime modifiedDateTime;
}
