package filters;

import play.filters.cors.CORSFilter;
import play.http.DefaultHttpFilters;
import play.mvc.EssentialFilter;

import javax.inject.Inject;

public class Filters extends DefaultHttpFilters {

    CORSFilter corsFilter;

    @Inject
    public Filters(CORSFilter corsFilter) {
        super(corsFilter);
        this.corsFilter = corsFilter;
    }

    public EssentialFilter[] filters() {
        return new EssentialFilter[]{corsFilter.asJava()};
    }
}