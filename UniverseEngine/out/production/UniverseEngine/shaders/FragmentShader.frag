#version 410

in vec2 passUV;
in vec3 vert;
in vec3 col_out;
out vec4 out_colour;

uniform sampler2D textureSampler;

void main(void){
      //out_colour = vec4(vert,1.0);
      
      //out_colour = vec4(col_out,1.0);
      out_colour = vec4(1.0,0.0,1.0,1.0);
      //out_colour = texture(textureSampler,passUV);
}
