#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dto.event;

/**
 * Event: 完成某个操作后触发的额外操作通过事件完成，事件实体在client中定义
 *
 * @author vince 2024-08-28 23:08
 */
public class CustomerCreatedEvent{

    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
