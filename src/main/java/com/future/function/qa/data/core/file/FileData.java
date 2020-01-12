package com.future.function.qa.data.core.file;

import com.future.function.qa.data.BaseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileData extends BaseData {}
