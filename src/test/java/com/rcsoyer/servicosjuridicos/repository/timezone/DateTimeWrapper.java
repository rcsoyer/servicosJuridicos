package com.rcsoyer.servicosjuridicos.repository.timezone;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "jhi_date_time_wrapper")
public class DateTimeWrapper implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    @Column(name = "instant")
    private Instant instant;
    
    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;
    
    @Column(name = "offset_date_time")
    private OffsetDateTime offsetDateTime;
    
    @Column(name = "zoned_date_time")
    private ZonedDateTime zonedDateTime;
    
    @Column(name = "local_time")
    private LocalTime localTime;
    
    @Column(name = "offset_time")
    private OffsetTime offsetTime;
    
    @Column(name = "local_date")
    private LocalDate localDate;
}
