#version 410

in vec3 out_colour;
out vec4 c;

void main(void){      
      c = vec4(out_colour,1.0);
}
