#version 410
in vec3 colour;
out vec4 out_colour;

void main(void){
      out_colour = vec4(colour,1.0);
      out_colour = vec4(1.0,0.0,1.0,1.0);
}
