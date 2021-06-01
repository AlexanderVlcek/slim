package myPackage.util;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class CustomFilter implements Filter {

    public static final String REQUEST_SEPARATOR = "\n##############################REQUEST##############################\n";
    public static final String RESPONSE_SEPARATOR = "\n##############################RESPONSE##############################\n";
    private String separatorType;

    public CustomFilter(String separatorType) {
        this.separatorType = separatorType;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        System.out.print(this.separatorType);
        return ctx.next(requestSpec, responseSpec);
    }
}