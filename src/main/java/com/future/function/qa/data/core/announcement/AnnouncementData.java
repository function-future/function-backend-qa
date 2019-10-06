package com.future.function.qa.data.core.announcement;

import com.future.function.qa.data.BaseData;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AnnouncementData extends BaseData {
}
