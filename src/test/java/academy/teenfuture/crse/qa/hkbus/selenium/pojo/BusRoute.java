package academy.teenfuture.crse.qa.hkbus.selenium.pojo;

public class BusRoute {

    public String route;
    public String bound;
    public String serviceType;
    public String originEn;
    public String originTc;
    public String originSc;
    public String destinationEn;
    public String destinationTc;
    public String destinationSc;

    public BusRoute() {}
    
    public BusRoute(String route, String bound, String serviceType, String originEn, String originTc, String originSc,
            String destinationEn, String destinationTc, String destinationSc) {
        this.route = route;
        this.bound = bound;
        this.serviceType = serviceType;
        this.originEn = originEn;
        this.originTc = originTc;
        this.originSc = originSc;
        this.destinationEn = destinationEn;
        this.destinationTc = destinationTc;
        this.destinationSc = destinationSc;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getBound() {
        return bound;
    }

    public void setBound(String bound) {
        this.bound = bound;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getOriginEn() {
        return originEn;
    }

    public void setOriginEn(String originEn) {
        this.originEn = originEn;
    }

    public String getOriginTc() {
        return originTc;
    }

    public void setOriginTc(String originTc) {
        this.originTc = originTc;
    }

    public String getOriginSc() {
        return originSc;
    }

    public void setOriginSc(String originSc) {
        this.originSc = originSc;
    }

    public String getDestinationEn() {
        return destinationEn;
    }

    public void setDestinationEn(String destinationEn) {
        this.destinationEn = destinationEn;
    }

    public String getDestinationTc() {
        return destinationTc;
    }

    public void setDestinationTc(String destinationTc) {
        this.destinationTc = destinationTc;
    }

    public String getDestinationSc() {
        return destinationSc;
    }

    public void setDestinationSc(String destinationSc) {
        this.destinationSc = destinationSc;
    }

    @Override
    public String toString() {
        return "Route [route=" + route + ", bound=" + bound + ", serviceType=" + serviceType + ", originEn=" + originEn
                + ", originTc=" + originTc + ", originSc=" + originSc + ", destinationEn=" + destinationEn
                + ", destinationTc=" + destinationTc + ", destinationSc=" + destinationSc + "]";
    }

    
}
