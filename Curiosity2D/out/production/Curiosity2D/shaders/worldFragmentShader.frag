#version 410

in vec3 c_out;
in vec2 tex;


out vec4 out_colour;

uniform sampler2D textureSampler;


void main(void){
       //out_colour = vec4(1.0,0.0,1.0,1.0);
       out_colour = vec4(c_out,1.0);
       //out_colour = texture(textureSampler,tex);
}