module calculator_display (
    input  wire       clk,
    input  wire       rst,
    input  wire       button,
    input  wire [31:0] cal_result,
    output reg [7:0] led_en,
	output reg       led_ca,
	output reg       led_cb,
    output reg       led_cc,
	output reg       led_cd,
	output reg       led_ce,
	output reg       led_cf,
	output reg       led_cg,
	output reg       led_dp
);

wire rst_n = ~rst;
//reg button_t = 1'b0;
reg [20:0] cnt = 21'b0;
reg [31:0] temp;
wire cnt_end = (cnt == 21'd20_000);
//wire cnt_end = (cnt == 21'd2);
reg [3:0] test;
reg [3:0] test1;
reg [3:0] test2;
reg [3:0] test3;
reg button_t = 0;

always @ (posedge clk or negedge rst_n) begin
    if (~rst_n) cnt <= 21'b0;
    else if (button || cnt_end) cnt <= 21'b0;
    else cnt <= cnt + 21'b1;
end


always @ (posedge clk or negedge rst_n) begin
    if (~rst_n) led_en <= 8'b11111111;
    else if (button) button_t <= 1;
    else if (button_t) begin 
        led_en <= 8'b01111111;
        temp <= cal_result;
//        test1 <= temp[3:0];
        button_t <= 0;
//        case (temp[3:0])
//            4'b0000:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {0,0,0,0,0,0,1};
//            4'b0001:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {1,0,0,1,1,1,1};
//            4'b0010:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {0,0,1,0,0,1,0};
//            4'b0011:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {0,0,0,0,1,1,0};
//            4'b0100:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {1,0,0,1,1,0,0};
//            4'b0101:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {0,1,0,0,0,1,0};
//            4'b0110:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {0,1,0,0,0,0,0};
//            4'b0111:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {0,0,0,1,1,1,1};
//            4'b1000:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {0,0,0,0,0,0,0};
//            4'b1001:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {0,0,0,1,1,0,0};
////            4'b1010:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {0,0,0,1,0,0,0};
//            4'b1010:test2 <= 4'b1010;
//            4'b1011:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {1,1,0,0,0,0,0};
//            4'b1100:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {1,1,1,0,0,1,0};
//            4'b1101:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {1,0,0,0,0,1,0};
//            4'b1110:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {0,1,1,0,0,0,0};
//            4'b1111:{led_ca,led_cb,led_cc,led_cd,led_ce,led_cf,led_cg} <= {0,1,1,1,0,0,0};
//            default:begin end
//        endcase
    end
    else if (cnt_end) begin
        test <= temp[3:0];
        case (temp[3:0])
            4'b0000:begin
                        led_ca <= 0;
                        led_cb <= 0;
                        led_cc <= 0;
                        led_cd <= 0;
                        led_ce <= 0;
                        led_cf <= 0;
                        led_cg <= 1;
                    end
            4'b0001:begin
                        led_ca <= 1;
                        led_cb <= 0;
                        led_cc <= 0;
                        led_cd <= 1;
                        led_ce <= 1;
                        led_cf <= 1;
                        led_cg <= 1;
                    end
            4'b0010:begin
                        led_ca <= 0;
                        led_cb <= 0;
                        led_cc <= 1;
                        led_cd <= 0;
                        led_ce <= 0;
                        led_cf <= 1;
                        led_cg <= 0;
                    end
            4'b0011:begin
                        led_ca <= 0;
                        led_cb <= 0;
                        led_cc <= 0;
                        led_cd <= 0;
                        led_ce <= 1;
                        led_cf <= 1;
                        led_cg <= 0;
                    end
            4'b0100:begin
                        led_ca <= 1;
                        led_cb <= 0;
                        led_cc <= 0;
                        led_cd <= 1;
                        led_ce <= 1;
                        led_cf <= 0;
                        led_cg <= 0;
                    end
            4'b0101:begin
                        led_ca <= 0;
                        led_cb <= 1;
                        led_cc <= 0;
                        led_cd <= 0;
                        led_ce <= 1;
                        led_cf <= 0;
                        led_cg <= 0;
                    end
            4'b0110:begin
                        led_ca <= 0;
                        led_cb <= 1;
                        led_cc <= 0;
                        led_cd <= 0;
                        led_ce <= 0;
                        led_cf <= 0;
                        led_cg <= 0;
                    end
            4'b0111:begin
                        led_ca <= 0;
                        led_cb <= 0;
                        led_cc <= 0;
                        led_cd <= 1;
                        led_ce <= 1;
                        led_cf <= 1;
                        led_cg <= 1;
                    end
            4'b1000:begin
                        led_ca <= 0;
                        led_cb <= 0;
                        led_cc <= 0;
                        led_cd <= 0;
                        led_ce <= 0;
                        led_cf <= 0;
                        led_cg <= 0;
                    end
            4'b1001:begin
                        led_ca <= 0;
                        led_cb <= 0;
                        led_cc <= 0;
                        led_cd <= 1;
                        led_ce <= 1;
                        led_cf <= 0;
                        led_cg <= 0;
                    end
            4'b1010:begin
                        led_ca <= 0;
                        led_cb <= 0;
                        led_cc <= 0;
                        led_cd <= 1;
                        led_ce <= 0;
                        led_cf <= 0;
                        led_cg <= 0;
                    end
            4'b1011:begin
                        led_ca <= 1;
                        led_cb <= 1;
                        led_cc <= 0;
                        led_cd <= 0;
                        led_ce <= 0;
                        led_cf <= 0;
                        led_cg <= 0;
                    end
            4'b1100:begin
                        led_ca <= 1;
                        led_cb <= 1;
                        led_cc <= 1;
                        led_cd <= 0;
                        led_ce <= 0;
                        led_cf <= 1;
                        led_cg <= 0;
                    end
            4'b1101:begin
                        led_ca <= 1;
                        led_cb <= 0;
                        led_cc <= 0;
                        led_cd <= 0;
                        led_ce <= 0;
                        led_cf <= 1;
                        led_cg <= 0;
                    end
            4'b1110:begin
                        led_ca <= 0;
                        led_cb <= 1;
                        led_cc <= 1;
                        led_cd <= 0;
                        led_ce <= 0;
                        led_cf <= 0;
                        led_cg <= 0;
                    end
            4'b1111:begin
                        led_ca <= 0;
                        led_cb <= 1;
                        led_cc <= 1;
                        led_cd <= 1;
                        led_ce <= 0;
                        led_cf <= 0;
                        led_cg <= 0;
                    end
            default:begin end
        endcase
        led_en <= {led_en[6:0],led_en[7]};
        temp <= {temp[3:0],temp[31:4]};
    end
end
endmodule
