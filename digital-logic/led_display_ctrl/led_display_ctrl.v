module led_display_ctrl (
    input  wire       clk   ,
	input  wire       rst   ,
	input  wire       button,
	output reg  [7:0] led_en,
	output reg        led_ca,
	output reg        led_cb,
    output reg        led_cc,
	output reg        led_cd,
	output reg        led_ce,
	output reg        led_cf,
	output reg        led_cg,
	output wire       led_dp 
);

assign led_dp=1'b1;
reg la;
reg lb;
reg lc;
reg ld;
reg le;
reg lf;
reg lg;

reg ra;
reg rb;
reg rc;
reg rd;
reg re;
reg rf;
reg rg;

integer i=10;
reg button_t=1'b0;
reg [20:0] cnt1=21'd0;
reg [29:0] cnt2=30'd0;
wire cnt_inc=(button_t);
//wire cnt1_end = cnt_inc & (cnt1 == 21'd5);
//wire cnt2_end = cnt_inc & (cnt2 == 30'd100);
wire cnt1_end = cnt_inc & (cnt1 == 21'd2_00_000);
wire cnt2_end = cnt_inc & (cnt2 == 30'd100_000_000);
wire rst_n = ~rst;

always @(posedge clk or negedge rst_n) begin    //cnt1
	if (~rst_n) cnt1 <= 21'd0;
	else if (cnt1_end) cnt1 <= 21'd0;
	else if (cnt_inc) cnt1 <= cnt1 + 27'd1;
end

always @(posedge clk or negedge rst_n) begin    //cnt2
	if (~rst_n) cnt2 <= 30'd0;
	else if (cnt2_end) cnt2 <= 30'd0;
	else if (cnt_inc) cnt2 <= cnt2 + 30'd1;
end

always @ (posedge clk or negedge rst_n) begin   //control led_en
    if (~rst_n) begin
        led_en <= 8'b11111111;
        button_t<=1'b0;
    end
    else if (button) begin
        led_en<=8'b11111110;
        button_t<=1'b1;
    end
    else if (button_t & cnt1_end) begin
        led_en<={led_en[6:0],led_en[7]};
    end
end

always @ (posedge clk or negedge rst_n) begin   //control countdown
    if (~rst_n) begin
    end
    else if (button) begin
        la<=1'b1;
        lb<=1'b0;
        lc<=1'b0;
        ld<=1'b1;
        le<=1'b1;
        lf<=1'b1;
        lg<=1'b1;
        ra<=1'b0;
        rb<=1'b0;
        rc<=1'b0;
        rd<=1'b0;
        re<=1'b0;
        rf<=1'b0;
        rg<=1'b1;
        i<=10;
    end
    else if (button_t & cnt2_end) begin
        case(i)
            10:begin
                la<=1'b0;
                lb<=1'b0;
                lc<=1'b0;
                ld<=1'b0;
                le<=1'b0;
                lf<=1'b0;
                lg<=1'b1;
                ra<=1'b0;
                rb<=1'b0;
                rc<=1'b0;
                rd<=1'b1;
                re<=1'b1;
                rf<=1'b0;
                rg<=1'b0;
                i<=9;
               end
            9:begin
                la<=1'b0;
                lb<=1'b0;
                lc<=1'b0;
                ld<=1'b0;
                le<=1'b0;
                lf<=1'b0;
                lg<=1'b1;
                ra<=1'b0;
                rb<=1'b0;
                rc<=1'b0;
                rd<=1'b0;
                re<=1'b0;
                rf<=1'b0;
                rg<=1'b0;
                i<=8;
                end
            8:begin
                la<=1'b0;
                lb<=1'b0;
                lc<=1'b0;
                ld<=1'b0;
                le<=1'b0;
                lf<=1'b0;
                lg<=1'b1;
                ra<=1'b0;
                rb<=1'b0;
                rc<=1'b0;
                rd<=1'b1;
                re<=1'b1;
                rf<=1'b1;
                rg<=1'b1;
                i<=7;
                end
            7:begin
                la<=1'b0;
                lb<=1'b0;
                lc<=1'b0;
                ld<=1'b0;
                le<=1'b0;
                lf<=1'b0;
                lg<=1'b1;
                ra<=1'b0;
                rb<=1'b1;
                rc<=1'b0;
                rd<=1'b0;
                re<=1'b0;
                rf<=1'b0;
                rg<=1'b0;
                i<=6;
                end
            6:begin
                la<=1'b0;
                lb<=1'b0;
                lc<=1'b0;
                ld<=1'b0;
                le<=1'b0;
                lf<=1'b0;
                lg<=1'b1;
                ra<=1'b0;
                rb<=1'b1;
                rc<=1'b0;
                rd<=1'b0;
                re<=1'b1;
                rf<=1'b0;
                rg<=1'b0;
                i<=5;
                end
            5:begin
                la<=1'b0;
                lb<=1'b0;
                lc<=1'b0;
                ld<=1'b0;
                le<=1'b0;
                lf<=1'b0;
                lg<=1'b1;
                ra<=1'b1;
                rb<=1'b0;
                rc<=1'b0;
                rd<=1'b1;
                re<=1'b1;
                rf<=1'b0;
                rg<=1'b0;
                i<=4;
                end
            4:begin
                la<=1'b0;
                lb<=1'b0;
                lc<=1'b0;
                ld<=1'b0;
                le<=1'b0;
                lf<=1'b0;
                lg<=1'b1;
                ra<=1'b0;
                rb<=1'b0;
                rc<=1'b0;
                rd<=1'b0;
                re<=1'b1;
                rf<=1'b1;
                rg<=1'b0;
                i<=3;
                end
            3:begin
                la<=1'b0;
                lb<=1'b0;
                lc<=1'b0;
                ld<=1'b0;
                le<=1'b0;
                lf<=1'b0;
                lg<=1'b1;
                ra<=1'b0;
                rb<=1'b0;
                rc<=1'b1;
                rd<=1'b0;
                re<=1'b0;
                rf<=1'b1;
                rg<=1'b0;
                i<=2;
                end
            2:begin
                la<=1'b0;
                lb<=1'b0;
                lc<=1'b0;
                ld<=1'b0;
                le<=1'b0;
                lf<=1'b0;
                lg<=1'b1;
                ra<=1'b1;
                rb<=1'b0;
                rc<=1'b0;
                rd<=1'b1;
                re<=1'b1;
                rf<=1'b1;
                rg<=1'b1;
                i<=1;
                end  
            1:begin
                la<=1'b0;
                lb<=1'b0;
                lc<=1'b0;
                ld<=1'b0;
                le<=1'b0;
                lf<=1'b0;
                lg<=1'b1;
                ra<=1'b0;
                rb<=1'b0;
                rc<=1'b0;
                rd<=1'b0;
                re<=1'b0;
                rf<=1'b0;
                rg<=1'b1;
                i<=0;
                end
            0:begin
                la<=1'b1;
                lb<=1'b0;
                lc<=1'b0;
                ld<=1'b1;
                le<=1'b1;
                lf<=1'b1;
                lg<=1'b1;
                ra<=1'b0;
                rb<=1'b0;
                rc<=1'b0;
                rd<=1'b0;
                re<=1'b0;
                rf<=1'b0;
                rg<=1'b1;
                i<=10;
                end
        endcase
    end
end

always @ (posedge clk or negedge rst_n) begin
    if (~rst_n) begin
    end
    else begin
    case(led_en)
        8'b11111110:begin
                        led_ca<=1'b0;
                        led_cb<=1'b0;
                        led_cc<=1'b0;
                        led_cd<=1'b0;
                        led_ce<=1'b1;
                        led_cf<=1'b1;
                        led_cg<=1'b0;
                    end
        8'b11111101:begin
                        led_ca<=1'b1;
                        led_cb<=1'b0;
                        led_cc<=1'b0;
                        led_cd<=1'b1;
                        led_ce<=1'b1;
                        led_cf<=1'b1;
                        led_cg<=1'b1;
                    end
        8'b11111011:begin
                        led_ca<=1'b0;
                        led_cb<=1'b0;
                        led_cc<=1'b1;
                        led_cd<=1'b0;
                        led_ce<=1'b0;
                        led_cf<=1'b1;
                        led_cg<=1'b0;
                    end
        8'b11110111:begin
                        led_ca<=1'b0;
                        led_cb<=1'b0;
                        led_cc<=1'b0;
                        led_cd<=1'b0;
                        led_ce<=1'b0;
                        led_cf<=1'b0;
                        led_cg<=1'b1;
                    end            
        8'b11101111:begin
                        led_ca<=1'b0;
                        led_cb<=1'b0;
                        led_cc<=1'b0;
                        led_cd<=1'b0;
                        led_ce<=1'b0;
                        led_cf<=1'b0;
                        led_cg<=1'b1;
                    end
        8'b11011111:begin
                        led_ca<=1'b0;
                        led_cb<=1'b0;
                        led_cc<=1'b1;
                        led_cd<=1'b0;
                        led_ce<=1'b0;
                        led_cf<=1'b1;
                        led_cg<=1'b0;
                    end            
        8'b10111111:begin
                        led_ca<=ra;
                        led_cb<=rb;
                        led_cc<=rc;
                        led_cd<=rd;
                        led_ce<=re;
                        led_cf<=rf;
                        led_cg<=rg;
                    end            
        8'b01111111:begin
                        led_ca<=la;
                        led_cb<=lb;
                        led_cc<=lc;
                        led_cd<=ld;
                        led_ce<=le;
                        led_cf<=lf;
                        led_cg<=lg;
                    end
        default:begin
                 end                       
    endcase
    end
end
//always @ (posedge clk or negedge rst_n) begin
//    if (~rst_n)    cnt
//end
//always @ (posedge clk or negedge rst_n) begin
//end
endmodule