module sequence_detection (
    input  wire       clk   ,
	input  wire       rst   ,
	input  wire       button,
	input  wire [7:0] switch,
	output reg        led
);

wire rst_n=~rst;

parameter IDLE = 3'b000;
parameter S0 = 3'b001;
parameter S1 = 3'b010;
parameter S2 = 3'b011;
parameter S3 = 3'b100;
parameter S4 = 3'b101;

reg [3:0] i;
reg [2:0] current_state=IDLE;
reg [2:0] next_state=IDLE;
reg out = 0;
reg button_en = 0;
wire end_state = (i == 4'b1111);

always @ (posedge clk or negedge rst_n) begin   // initial
    if (~rst_n) begin
        current_state <= IDLE; 
        button_en <= 0;
        i <= 9;  
        led <= 0;
        end
    else if (button) begin 
        current_state <= IDLE;
        button_en <= 1;
        i <= 8;
        led <= 0;
        end
    else if (end_state || led==1) begin
        button_en <= 0;
    end
    else if (button_en) begin
        i <= i-1;
        current_state <= next_state;     
        led <= out;
    end
end

always @ (i) begin
    case (current_state)
        IDLE : if (button_en) begin 
                 next_state = S0;
                 out = 0;
               end 
        S0 : if (end_state) begin 
                next_state = IDLE;
                end
             else if (switch[i]) next_state = S1;
             else next_state = S0;
        S1 : if (end_state) begin 
                next_state = IDLE;
                end
             else if (switch[i]) next_state = S1;
             else begin
                next_state = S2;
                end
        S2 : if (end_state) begin 
                next_state = IDLE;
                end
             else if (switch[i]) next_state = S1;
             else next_state = S3;
        S3 : if (end_state) begin 
                next_state = IDLE;
                end
             else if (switch[i]) next_state = S4;
             else next_state = S0;
        S4 : if (end_state || switch[i]) begin 
                next_state = IDLE;
                end
             else begin 
                    next_state = S0;
                    out = 1;
                  end
        default : next_state = IDLE;
    endcase
end
endmodule