package uk.gov.digital.ho.hocs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class DashboardSummaryDto {

    ArrayList<HashMap> summary;


}
